package by.zemich.telegrambotservice.domain.policy;


import by.zemich.telegrambotservice.domain.model.KufarAdvertisement;
import by.zemich.telegrambotservice.domain.policy.api.Policy;

public class CategoryPolicy implements Policy<KufarAdvertisement> {
    private final String categoryId;

    public CategoryPolicy(String categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public boolean isSatisfiedBy(KufarAdvertisement kufarAdvertisement) {
        if (!isApplicable(kufarAdvertisement)) return false;
        return kufarAdvertisement.getCategory().equalsIgnoreCase(categoryId);
    }

    private boolean isApplicable(KufarAdvertisement kufarAdvertisement) {
        if (kufarAdvertisement == null) {
            return false;
        }

        String category = kufarAdvertisement.getCategory();
        return category != null && !category.trim().isEmpty();
    }
}
