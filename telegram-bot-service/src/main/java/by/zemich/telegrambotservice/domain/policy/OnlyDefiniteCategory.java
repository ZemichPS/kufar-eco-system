package by.zemich.telegrambotservice.domain.policy;


import by.zemich.telegrambotservice.domain.model.KufarAdvertisement;
import by.zemich.telegrambotservice.domain.policy.api.Policy;

public class OnlyDefiniteCategory implements Policy<KufarAdvertisement> {

    private final String category;

    public OnlyDefiniteCategory(String category) {
        this.category = category;
    }

    @Override
    public boolean isSatisfiedBy(KufarAdvertisement kufarAdvertisement) {
        return category.equalsIgnoreCase(kufarAdvertisement.getCategory());
    }
}
