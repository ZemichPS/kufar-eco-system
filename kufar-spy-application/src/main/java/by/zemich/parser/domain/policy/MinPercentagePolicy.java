package by.zemich.parser.domain.policy;

import by.zemich.parser.application.service.MarketPriceService;
import by.zemich.parser.domain.model.Advertisement;
import by.zemich.parser.application.service.AdvertisementService;
import by.zemich.parser.domain.service.PriceAnalyzer;
import by.zemich.parser.domain.policy.api.Policy;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Predicate;

public class MinPercentagePolicy implements Policy<Advertisement> {

    private final BigDecimal minPercentage;
    private final PriceAnalyzer priceAnalyzer;
    private final AdvertisementService advertisementService;
    private final MinimumRequredAmountOfDataForMarketPriceCountingPolicy minDataSize = new MinimumRequredAmountOfDataForMarketPriceCountingPolicy();
    private final MarketPriceService marketPriceService;

    protected final Predicate<Advertisement> fullFunctionalPredicate = Advertisement::isFullyFunctional;


    public MinPercentagePolicy(BigDecimal minPercentage,
                               PriceAnalyzer priceAnalyzer,
                               AdvertisementService advertisementService, MarketPriceService marketPriceService
    ) {
        this.minPercentage = minPercentage;
        this.priceAnalyzer = priceAnalyzer;
        this.advertisementService = advertisementService;
        this.marketPriceService = marketPriceService;
    }

    @Override
    public boolean isSatisfiedBy(Advertisement advertisement) {
        BigDecimal currentAdPrice = advertisement.getPriceInByn();

        if (currentAdPrice.compareTo(BigDecimal.ZERO) == 0) return false;
        if (advertisement.getBrand().isEmpty() || advertisement.getModel().isEmpty()) return false;

        String memoryAmount = advertisement.getParameterValueByParameterName("phablet_phones_memory").orElse("");
        if (memoryAmount.isEmpty()) return false;

        String brand = advertisement.getBrand().orElse("");
        String model = advertisement.getModel().orElse("");

        List<Advertisement> advertisements = advertisementService.getAllByBrandAndModelWithMemoryAmount(brand, model, memoryAmount);
        if (!minDataSize.isSatisfiedBy(advertisements.size())) return false;

        BigDecimal marketPriceForCommerce;
        try {
            marketPriceForCommerce = marketPriceService.getMarketPrice(
                    advertisements,
                    fullFunctionalPredicate,
                    advertisement.getCondition()
            );
        } catch (Exception e) {
            return false;
        }

        BigDecimal percentageDifference = priceAnalyzer.calculatePercentageDifference(marketPriceForCommerce, currentAdPrice);
        return percentageDifference.compareTo(BigDecimal.ZERO) == -1
                && percentageDifference.compareTo(minPercentage) == -1;
    }

}