package by.zemich.telegrambotservice.domain.policy;


import by.zemich.telegrambotservice.domain.model.KufarAdvertisement;
import by.zemich.telegrambotservice.domain.policy.api.Policy;

import java.util.List;

public class SmartphoneMemoryCapacityAdsPolicy implements Policy<KufarAdvertisement> {

    private final List<String> memoryCapacities;

    public SmartphoneMemoryCapacityAdsPolicy(List<String> memoryCapacities) {
        this.memoryCapacities = memoryCapacities;
    }

    @Override
    public boolean isSatisfiedBy(KufarAdvertisement kufarAdvertisement) {
        String memoryCapacity = kufarAdvertisement.getParameterValueByParameterName("phablet_phones_memory")
                .map(value -> value.split(" ")[0])
                .orElse("");
        return memoryCapacities.contains(memoryCapacity);
    }
}
