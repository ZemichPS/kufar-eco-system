package by.zemich.telegrambotservice.domain.policy;


import by.zemich.telegrambotservice.domain.model.KufarAdvertisement;
import by.zemich.telegrambotservice.domain.policy.api.Policy;

public class OnlyOwnersAds implements Policy<KufarAdvertisement> {
    @Override
    public boolean isSatisfiedBy(KufarAdvertisement kufarAdvertisement) {
        return !kufarAdvertisement.isCompanyAd();
    }
}
