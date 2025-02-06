package by.zemich.telegrambotservice.domain.policy;


import by.zemich.telegrambotservice.domain.model.KufarAdvertisement;
import by.zemich.telegrambotservice.domain.policy.api.Policy;

import java.util.List;

public class OnlyDefiniteBrandAndModelAdsPolicy implements Policy<KufarAdvertisement> {

    private final String BRAND_NAME;
    private final List<String> models;

    public OnlyDefiniteBrandAndModelAdsPolicy(String brandName, List<String> models) {
        BRAND_NAME = brandName;
        this.models = models;
    }

    @Override
    public boolean isSatisfiedBy(KufarAdvertisement kufarAdvertisement) {
        String model = kufarAdvertisement.getModel().orElse("");
        String brand = kufarAdvertisement.getBrand().orElse("");
        return brand.equalsIgnoreCase(BRAND_NAME) &&  models.contains(model);
    }
}
