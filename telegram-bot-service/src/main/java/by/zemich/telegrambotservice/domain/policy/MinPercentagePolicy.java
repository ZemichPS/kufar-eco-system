package by.zemich.telegrambotservice.domain.policy;


import by.zemich.telegrambotservice.application.service.MarketPriceService;
import by.zemich.telegrambotservice.domain.model.KufarAdvertisement;
import by.zemich.telegrambotservice.domain.policy.api.Policy;
import by.zemich.telegrambotservice.infrastructure.clients.AdvertisementClient;
import by.zemich.telegrambotservice.infrastructure.clients.AdvertisementDevicesServiceFeignClient;
import org.glassfish.grizzly.utils.Futures;

import java.math.BigDecimal;
import java.util.List;

public class MinPercentagePolicy implements Policy<KufarAdvertisement> {

    private final MinimumRequredAmountOfDataForMarketPriceCountingPolicy minDataSize = new MinimumRequredAmountOfDataForMarketPriceCountingPolicy();
    private final BigDecimal minPercentage;
    private final AdvertisementClient advertisementClient;
    private final MarketPriceService marketPriceService;

    public MinPercentagePolicy(BigDecimal minPercentage,
                               AdvertisementClient advertisementClient,
                               MarketPriceService marketPriceService
    ) {
        this.minPercentage = minPercentage;
        this.advertisementClient = advertisementClient;
        this.marketPriceService = marketPriceService;
    }


    @Override
    public boolean isSatisfiedBy(KufarAdvertisement kufarAdvertisement) {
        BigDecimal currentAdPrice = kufarAdvertisement.getPriceInByn();

        if (currentAdPrice.compareTo(BigDecimal.ZERO) == 0) return false;
        if (kufarAdvertisement.getBrand().isEmpty() || kufarAdvertisement.getModel().isEmpty()) return false;

        String memoryAmount = kufarAdvertisement.getParameterValueByParameterName("phablet_phones_memory").orElse("");
        String ram = kufarAdvertisement.getParameterValueByParameterName("ram").orElse("");
        if (memoryAmount.isEmpty()) return false;

        String brand = kufarAdvertisement.getBrand().orElse("");
        String model = kufarAdvertisement.getModel().orElse("");

        List<BigDecimal> prices = (, model, memoryAmount)
                .getPricesInByn();
        if (!minDataSize.isSatisfiedBy(prices.size())) return false;

        BigDecimal marketPriceForCommerce;
        try {
            marketPriceForCommerce = marketPriceService.getMarketPrice(prices);
        } catch (Exception e) {
            return false;
        }

        BigDecimal percentageDifference = marketPriceService.calculatePercentageDifference(marketPriceForCommerce, currentAdPrice);
        return percentageDifference.compareTo(BigDecimal.ZERO) == -1
                && percentageDifference.compareTo(minPercentage) == -1;
    }

}