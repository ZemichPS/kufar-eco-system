package by.zemich.telegrambotservice.domain.policy;


import by.zemich.telegrambotservice.domain.model.KufarAdvertisement;
import by.zemich.telegrambotservice.domain.policy.api.Policy;

import java.util.Objects;
import java.util.Optional;

public class AreaPolicy implements Policy<KufarAdvertisement> {

    private final String targetArea;

    public AreaPolicy(String targetArea) {
        this.targetArea = Objects.requireNonNull(targetArea, "Target area must not be null");
    }

    @Override
    public boolean isSatisfiedBy(KufarAdvertisement kufarAdvertisement) {
        if(!isApplicable(kufarAdvertisement)) return false;

        Optional<KufarAdvertisement.Parameter> areaParameter = kufarAdvertisement.getParameterByIdentity("area");
        return areaParameter.map(param-> param.getValue()
                .equalsIgnoreCase(targetArea))
                .orElse(false);
    }

    private boolean isApplicable(KufarAdvertisement kufarAdvertisement) {
        return kufarAdvertisement.getParameterByIdentity("area").isPresent();
    }
}
