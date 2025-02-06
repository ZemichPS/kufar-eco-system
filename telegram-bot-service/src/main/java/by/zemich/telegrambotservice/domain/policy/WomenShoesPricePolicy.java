package by.zemich.telegrambotservice.domain.policy;


import by.zemich.telegrambotservice.domain.model.KufarAdvertisement;
import by.zemich.telegrambotservice.domain.policy.api.Policy;

import java.math.BigDecimal;
import java.util.Map;

public class WomenShoesPricePolicy implements Policy<KufarAdvertisement> {

    private final Map<String, BigDecimal> pricesMap;

    public WomenShoesPricePolicy(Map<String, BigDecimal> pricesMap) {
        this.pricesMap = pricesMap;
    }

    @Override
    public boolean isSatisfiedBy(KufarAdvertisement kufarAdvertisement) {
        if (!isApplicable(kufarAdvertisement)) return false;

        BigDecimal currentPriceOfClothing = kufarAdvertisement.getPriceInByn();

        return kufarAdvertisement.getParameterByIdentity("women_shoes_type")
                .map(KufarAdvertisement.Parameter::getValue)
                .map(String::toLowerCase)
                .map(clothingType -> pricesMap.getOrDefault(clothingType, new BigDecimal(50)))
                .map(maxPrice -> currentPriceOfClothing.compareTo(maxPrice) <= 0)
                .orElse(false);
    }

    private boolean isApplicable(KufarAdvertisement kufarAdvertisement) {
        return kufarAdvertisement.getParameterByIdentity("women_shoes_type").isPresent();
    }
}
