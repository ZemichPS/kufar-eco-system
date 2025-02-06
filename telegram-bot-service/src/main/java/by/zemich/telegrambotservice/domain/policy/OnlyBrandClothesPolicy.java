package by.zemich.telegrambotservice.domain.policy;


import by.zemich.telegrambotservice.domain.model.KufarAdvertisement;
import by.zemich.telegrambotservice.domain.policy.api.Policy;

public class OnlyBrandClothesPolicy implements Policy<KufarAdvertisement> {
    @Override
    public boolean isSatisfiedBy(KufarAdvertisement kufarAdvertisement) {
        if(kufarAdvertisement.getParameterValueByParameterName("women_clothes_brand").isPresent()) {
            String brand = kufarAdvertisement.getParameterValueByParameterName("women_clothes_brand").get();
            return !brand.equalsIgnoreCase("Другой") && !brand.isEmpty();
        }
        return false;
    }
}
