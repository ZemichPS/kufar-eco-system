package by.zemich.telegrambotservice.domain.policy;


import by.zemich.telegrambotservice.domain.model.KufarAdvertisement;
import by.zemich.telegrambotservice.domain.policy.api.Policy;
import by.zemich.telegrambotservice.domain.service.PriceAnalyzer;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

@Slf4j
public class MinPercentagePolicy implements Policy<KufarAdvertisement> {

    private final MinimumRequredAmountOfDataForMarketPriceCountingPolicy minDataSize = new MinimumRequredAmountOfDataForMarketPriceCountingPolicy();
    private final BigDecimal minPercentage;
    private final PriceAnalyzer priceAnalyzer;

    public MinPercentagePolicy(
            BigDecimal minPercentage, PriceAnalyzer priceAnalyzer
    ) {
        this.priceAnalyzer = priceAnalyzer;
        this.minPercentage = minPercentage;
    }


    @Override
    public boolean isSatisfiedBy(KufarAdvertisement ad) {
        if (!isValidAdvertisement(ad)) {
            return false;
        }
        BigDecimal nonCommerceMarketPrice = ad.getNonCommerceMarketPrice().orElseThrow();
        BigDecimal currentAdPrice = ad.getPriceInByn();
        BigDecimal percentageDifference = priceAnalyzer.calculatePercentageDifference(nonCommerceMarketPrice, currentAdPrice);
        return percentageDifference.compareTo(BigDecimal.ZERO) == -1
                && percentageDifference.compareTo(minPercentage) == -1;
    }

    private boolean isValidAdvertisement(KufarAdvertisement ad) {
        return ad.getNonCommerceMarketPrice().isPresent() &&
                ad.getNonCommerceMarketPrice().get().signum() > 0 &&
                ad.getPriceInByn().signum() > 0;
    }

}