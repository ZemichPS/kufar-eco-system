package by.zemich.telegrambotservice.domain.policy;


import by.zemich.telegrambotservice.domain.model.KufarAdvertisement;
import by.zemich.telegrambotservice.domain.policy.api.Policy;

import java.util.List;

public class OnlyDefiniteBranAndModelListAdsPolicy implements Policy<KufarAdvertisement> {

    private final String BRAND_NAME;
    private final List<String> modelList;

    public OnlyDefiniteBranAndModelListAdsPolicy(String brandName, List<String> modelList) {
        BRAND_NAME = brandName;
        this.modelList = modelList;
    }

    @Override
    public boolean isSatisfiedBy(KufarAdvertisement kufarAdvertisement) {
        boolean modelFound = modelList.contains(kufarAdvertisement.getModel());
        return kufarAdvertisement.getBrand().equals(BRAND_NAME) && modelFound;
    }
}
