package by.zemich.parser.application.service.channels.api;

import by.zemich.parser.application.service.api.AdvertisementPublisher;
import by.zemich.parser.application.service.api.Notifiable;
import by.zemich.parser.domain.model.Advertisement;
import by.zemich.parser.domain.policy.api.Policy;
import by.zemich.parser.domain.service.PolicyChecker;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public abstract class Channel implements AdvertisementPublisher, Notifiable, ChannelApi {

    protected final PolicyChecker<Advertisement> policyChecker;

    protected Channel() {
        this.policyChecker = new PolicyChecker<>();
    }

    protected boolean checkPolicies(Advertisement advertisement) {
        return policyChecker.checkAll(advertisement);
    }

    protected  abstract List<Policy<Advertisement>> createPolicies();

    @PostConstruct
    private void setPolicies(){
        List<Policy<Advertisement>> policies = createPolicies();
        if (policies == null) {
            throw new IllegalStateException("Policies cannot be null");
        }
        policyChecker.initializePolicies(policies);
    }
}
