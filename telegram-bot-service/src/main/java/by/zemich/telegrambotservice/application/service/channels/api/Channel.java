package by.zemich.telegrambotservice.application.service.channels.api;

import by.zemich.telegrambotservice.application.service.api.AdvertisementPublisher;
import by.zemich.telegrambotservice.application.service.api.Notifiable;
import by.zemich.telegrambotservice.domain.model.KufarAdvertisement;
import by.zemich.telegrambotservice.domain.policy.api.Policy;
import by.zemich.telegrambotservice.domain.service.PolicyChecker;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public abstract class Channel implements AdvertisementPublisher, Notifiable, ChannelApi {

    protected final PolicyChecker<KufarAdvertisement> policyChecker;

    protected Channel() {
        this.policyChecker = new PolicyChecker<>();
    }

    protected boolean checkPolicies(KufarAdvertisement kufarAdvertisement) {
        return policyChecker.checkAll(kufarAdvertisement);
    }

    protected  abstract List<Policy<KufarAdvertisement>> createPolicies();

    @PostConstruct
    private void setPolicies(){
        List<Policy<KufarAdvertisement>> policies = createPolicies();
        if (policies == null) {
            throw new IllegalStateException("Policies cannot be null");
        }
        policyChecker.initializePolicies(policies);
    }
}
