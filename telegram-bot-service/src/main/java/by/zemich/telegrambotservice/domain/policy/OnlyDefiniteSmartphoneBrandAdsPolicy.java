package by.zemich.telegrambotservice.domain.policy;


import by.zemich.telegrambotservice.domain.model.KufarAdvertisement;
import by.zemich.telegrambotservice.domain.policy.api.Policy;

public class OnlyDefiniteSmartphoneBrandAdsPolicy implements Policy<KufarAdvertisement> {

    private final String BRAND_NAME;

    public OnlyDefiniteSmartphoneBrandAdsPolicy(String brandName) {
        BRAND_NAME = brandName;
    }

    @Override
    public boolean isSatisfiedBy(KufarAdvertisement kufarAdvertisement) {
       return kufarAdvertisement.getBrand()
               .map(brand -> brand.equalsIgnoreCase(BRAND_NAME))
               .orElse(false);
    }


}
