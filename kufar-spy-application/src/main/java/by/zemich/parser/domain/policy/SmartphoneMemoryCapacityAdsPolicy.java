package by.zemich.parser.domain.policy;

import by.zemich.parser.domain.model.Advertisement;
import by.zemich.parser.domain.policy.api.Policy;

import java.util.List;

public class SmartphoneMemoryCapacityAdsPolicy implements Policy<Advertisement> {

    private final List<String> memoryCapacities;

    public SmartphoneMemoryCapacityAdsPolicy(List<String> memoryCapacities) {
        this.memoryCapacities = memoryCapacities;
    }

    @Override
    public boolean isSatisfiedBy(Advertisement advertisement) {
        String memoryCapacity = advertisement.getParameterValueByParameterName("phablet_phones_memory")
                .map(value -> value.split(" ")[0])
                .orElse("");
        return memoryCapacities.contains(memoryCapacity);
    }
}
