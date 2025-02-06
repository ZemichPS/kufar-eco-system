package by.zemich.telegrambotservice.domain.policy;


import by.zemich.telegrambotservice.domain.model.KufarAdvertisement;
import by.zemich.telegrambotservice.domain.policy.api.Policy;

public class OnlyDefiniteBrandAdsPolicy implements Policy<KufarAdvertisement> {

    private final String BRAND_NAME;

    public OnlyDefiniteBrandAdsPolicy(String brandName) {
        BRAND_NAME = brandName;
    }

    @Override
    public boolean isSatisfiedBy(KufarAdvertisement kufarAdvertisement) {
        return kufarAdvertisement.getBrand().equals(BRAND_NAME);
    }
}
