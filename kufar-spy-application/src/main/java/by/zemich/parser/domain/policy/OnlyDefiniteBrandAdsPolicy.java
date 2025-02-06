package by.zemich.parser.domain.policy;

import by.zemich.parser.domain.model.Advertisement;
import by.zemich.parser.domain.policy.api.Policy;

public class OnlyDefiniteBrandAdsPolicy implements Policy<Advertisement> {

    private final String BRAND_NAME;

    public OnlyDefiniteBrandAdsPolicy(String brandName) {
        BRAND_NAME = brandName;
    }

    @Override
    public boolean isSatisfiedBy(Advertisement advertisement) {
        return advertisement.getBrand().equals(BRAND_NAME);
    }
}
