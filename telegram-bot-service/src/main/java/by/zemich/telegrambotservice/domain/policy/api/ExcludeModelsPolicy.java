package by.zemich.telegrambotservice.domain.policy.api;

import by.zemich.telegrambotservice.domain.model.KufarAdvertisement;

import java.util.List;

public class ExcludeModelsPolicy implements Policy<KufarAdvertisement> {

    private final List<String> blacklist;

    public ExcludeModelsPolicy(List<String> blacklist) {
        this.blacklist = blacklist;
    }

    @Override
    public boolean isSatisfiedBy(KufarAdvertisement advertisement) {
        return !advertisement.getModel()
                .map(String::trim)
                .map(blacklist::contains)
                .orElse(false);
    }

}
