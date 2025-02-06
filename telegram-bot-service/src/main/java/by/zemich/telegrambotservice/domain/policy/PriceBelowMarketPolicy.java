package by.zemich.telegrambotservice.domain.policy;

import by.zemich.telegrambotservice.domain.model.KufarAdvertisement;
import by.zemich.telegrambotservice.domain.policy.api.Policy;
import by.zemich.telegrambotservice.domain.service.PriceAnalyzer;
import by.zemich.telegrambotservice.infrastructure.clients.AdvertisementDevicesServiceFeignClient;
import lombok.SneakyThrows;

import java.math.BigDecimal;
import java.util.stream.Collectors;

public class PriceBelowMarketPolicy implements Policy<KufarAdvertisement> {
    private final PriceAnalyzer priceAnalyzer;
    private final AdvertisementDevicesServiceFeignClient advertisementService;

    public PriceBelowMarketPolicy(PriceAnalyzer priceAnalyzer, AdvertisementDevicesServiceFeignClient advertisementService) {
        this.priceAnalyzer = priceAnalyzer;
        this.advertisementService = advertisementService;
    }


    @SneakyThrows
    @Override
    public boolean isSatisfiedBy(KufarAdvertisement kufarAdvertisement) {
        BigDecimal productPrice = kufarAdvertisement.getPriceInByn();

        if(kufarAdvertisement.getModel().isEmpty()) return false;
        if(kufarAdvertisement.getBrand().isEmpty()) return false;
        // TODO ЗАМЕНИТЬ параметр
        if(kufarAdvertisement.getParameterByIdentity("phablet_phones_memory").isEmpty()) return false;

        String model = kufarAdvertisement.getModel().get();
        String brand = kufarAdvertisement.getBrand().get();
        String memory = kufarAdvertisement.getParameterByIdentity("phablet_phones_memory").get().getValue();

        BigDecimal marketPrice = advertisementService.getAdvertisementPriceListByBranModelMemoryAmount(brand, model, memory).getPricesInByn().stream()
                .filter(price -> price.compareTo(BigDecimal.ZERO) > 0)
                .collect(Collectors.collectingAndThen(Collectors.toList(), priceAnalyzer::getMarketPrice));

            return marketPrice.compareTo(productPrice) >= 0;
    }
}
