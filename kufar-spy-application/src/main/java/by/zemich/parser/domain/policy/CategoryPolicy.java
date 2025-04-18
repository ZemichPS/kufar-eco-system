package by.zemich.parser.domain.policy;

import by.zemich.parser.domain.model.Advertisement;
import by.zemich.parser.domain.policy.api.Policy;

public class CategoryPolicy implements Policy<Advertisement> {
    private final String categoryId;

    public CategoryPolicy(String categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public boolean isSatisfiedBy(Advertisement advertisement) {
        if (!isApplicable(advertisement)) return false;
        return advertisement.getCategory().equalsIgnoreCase(categoryId);
    }

    private boolean isApplicable(Advertisement advertisement) {
        if (advertisement == null) {
            return false;
        }

        String category = advertisement.getCategory();
        return category != null && !category.trim().isEmpty();
    }
}
