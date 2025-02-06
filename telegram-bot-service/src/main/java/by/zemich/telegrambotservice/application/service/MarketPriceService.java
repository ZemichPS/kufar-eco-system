package by.zemich.telegrambotservice.application.service;

import by.zemich.telegrambotservice.domain.model.KufarAdvertisement;
import by.zemich.telegrambotservice.domain.service.PriceAnalyzer;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

@Service
@AllArgsConstructor
public class MarketPriceService {
    private final int adsCountForMarketPriceAnalyze = 35;
    private final PriceAnalyzer priceAnalyzer;


    public BigDecimal getMarketPrice(List<KufarAdvertisement> kufarAdvertisements,
                                      Predicate<KufarAdvertisement> predicate,
                                      String condition
    ) throws Exception {
        List<BigDecimal> prices = kufarAdvertisements.stream()
                .filter(predicate)
                .filter(ad -> ad.getCondition().equalsIgnoreCase(condition))
                .sorted(Comparator.comparing(KufarAdvertisement::getPublishedAt).reversed())
                .limit(adsCountForMarketPriceAnalyze)
                .map(KufarAdvertisement::getPriceInByn)
                .toList();
        return priceAnalyzer.getMarketPrice(prices);
    }

    public BigDecimal getMarketPrice(List<BigDecimal> prices) throws Exception {
        return priceAnalyzer.getMarketPrice(prices);
    }

    public BigDecimal calculatePercentageDifference(BigDecimal val1, BigDecimal val2){
        return priceAnalyzer.calculatePercentageDifference(val1, val2);
    }
}
