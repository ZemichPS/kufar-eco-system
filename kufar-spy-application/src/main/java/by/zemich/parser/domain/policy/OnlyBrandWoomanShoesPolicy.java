package by.zemich.parser.domain.policy;

import by.zemich.parser.domain.model.Advertisement;
import by.zemich.parser.domain.policy.api.Policy;

public class OnlyBrandWoomanShoesPolicy implements Policy<Advertisement> {
    @Override
    public boolean isSatisfiedBy(Advertisement advertisement) {
        return "8100".equalsIgnoreCase(advertisement.getCategory())
                && advertisement.getParameterValueByParameterName("women_shoes_brand").isPresent();
    }
}
