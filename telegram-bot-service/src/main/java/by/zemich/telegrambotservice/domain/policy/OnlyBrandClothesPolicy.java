package by.zemich.telegrambotservice.domain.policy;


import by.zemich.telegrambotservice.domain.model.Advertisement;
import by.zemich.telegrambotservice.domain.policy.api.Policy;

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
