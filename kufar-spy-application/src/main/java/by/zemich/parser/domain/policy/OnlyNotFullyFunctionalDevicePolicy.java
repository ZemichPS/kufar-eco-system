package by.zemich.parser.domain.policy;

import by.zemich.parser.domain.model.Advertisement;
import by.zemich.parser.domain.policy.api.Policy;

public class OnlyNotFullyFunctionalDevicePolicy implements Policy<Advertisement> {
    @Override
    public boolean isSatisfiedBy(Advertisement advertisement) {
        return !advertisement.isFullyFunctional();
    }
}
