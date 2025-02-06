package by.zemich.telegrambotservice.domain.policy;


import by.zemich.telegrambotservice.domain.model.KufarAdvertisement;
import by.zemich.telegrambotservice.domain.policy.api.Policy;

import java.math.BigDecimal;

public class MinPriceForNewGoodsPolicy implements Policy<KufarAdvertisement> {

    private final BigDecimal minPrice;

    public MinPriceForNewGoodsPolicy(BigDecimal minPrice) {
        this.minPrice = minPrice;
    }

    @Override
    public boolean isSatisfiedBy(KufarAdvertisement kufarAdvertisement) {
        return !kufarAdvertisement.getCondition().equalsIgnoreCase("новое") ||
                kufarAdvertisement.getPriceInByn().compareTo(minPrice) >= 0;
    }
}
