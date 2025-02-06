package by.zemich.telegrambotservice.domain.policy;


import by.zemich.telegrambotservice.domain.model.KufarAdvertisement;
import by.zemich.telegrambotservice.domain.policy.api.Policy;

import java.util.List;
import java.util.Objects;

public class ExcludedWomenClosesBrandPolicy implements Policy<KufarAdvertisement> {
    private final List<String> excludedBrands;

    public ExcludedWomenClosesBrandPolicy(List<String> excludedBrands) {
        this.excludedBrands = excludedBrands;
    }

    @Override
    public boolean isSatisfiedBy(KufarAdvertisement kufarAdvertisement) {
        if (!isApplicable(kufarAdvertisement)) return false;

        return kufarAdvertisement.getParameterByIdentity("women_clothes_brand")
                .map(param -> excludedBrands.stream()
                        .map(String::toLowerCase)
                        .noneMatch(b -> b.equals(param.getValue().toLowerCase())))
                .orElse(false);

    }


    private boolean isApplicable(KufarAdvertisement kufarAdvertisement) {
        if (Objects.isNull(kufarAdvertisement)) return false;
        return kufarAdvertisement.getParameterValueByParameterName("women_clothes_brand")
                .map(param -> !param.isEmpty() && !param.isBlank())
                .orElse(false);
    }
}
