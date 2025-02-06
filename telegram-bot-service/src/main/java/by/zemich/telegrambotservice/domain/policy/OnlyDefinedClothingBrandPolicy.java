package by.zemich.telegrambotservice.domain.policy;


import by.zemich.telegrambotservice.domain.model.KufarAdvertisement;
import by.zemich.telegrambotservice.domain.policy.api.Policy;

import java.util.List;
import java.util.Objects;

public class OnlyDefinedClothingBrandPolicy implements Policy<KufarAdvertisement> {

    private final List<String> approvedBrands;

    public OnlyDefinedClothingBrandPolicy(List<String> approvedBrands) {
        this.approvedBrands = approvedBrands;
    }

    @Override
    public boolean isSatisfiedBy(KufarAdvertisement kufarAdvertisement) {
        if(!isApplicable(kufarAdvertisement)) return false;

        return approvedBrands.stream()
                .map(String::toLowerCase)
                .anyMatch(approvedBrand -> approvedBrand.equalsIgnoreCase(kufarAdvertisement.getParameterValueByParameterName("women_clothes_brand").orElse("").toLowerCase()));
    }

    private boolean isApplicable(KufarAdvertisement kufarAdvertisement) {
        if (Objects.isNull(kufarAdvertisement)) return false;
        return kufarAdvertisement.getParameterValueByParameterName("women_clothes_brand")
                .map(param -> !param.isEmpty() && !param.isBlank())
                .orElse(false);
    }
}
