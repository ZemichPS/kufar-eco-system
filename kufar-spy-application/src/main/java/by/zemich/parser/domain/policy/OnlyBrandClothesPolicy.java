package by.zemich.parser.domain.policy;

import by.zemich.parser.domain.model.Advertisement;
import by.zemich.parser.domain.policy.api.Policy;

public class OnlyBrandClothesPolicy implements Policy<Advertisement> {
    @Override
    public boolean isSatisfiedBy(Advertisement advertisement) {
        if(advertisement.getParameterValueByParameterName("women_clothes_brand").isPresent()) {
            String brand = advertisement.getParameterValueByParameterName("women_clothes_brand").get();
            return !brand.equalsIgnoreCase("Другой") && !brand.isEmpty();
        }
        return false;
    }
}
