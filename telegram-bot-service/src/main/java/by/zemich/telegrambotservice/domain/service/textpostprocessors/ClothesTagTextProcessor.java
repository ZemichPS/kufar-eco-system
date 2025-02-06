package by.zemich.telegrambotservice.domain.service.textpostprocessors;

import by.zemich.telegrambotservice.domain.model.KufarAdvertisement;
import by.zemich.telegrambotservice.domain.service.textpostprocessors.api.PostTextProcessor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(12)
public class ClothesTagTextProcessor implements PostTextProcessor {

    @Override
    public String process(KufarAdvertisement kufarAdvertisement) {
        String value = kufarAdvertisement.getParameterValueByParameterName("women_clothes_type").get();
        if (value.isEmpty() || value.isBlank()) return "";
        return PostTextProcessor.getTag(value);
    }

    @Override
    public boolean isApplicable(KufarAdvertisement kufarAdvertisement) {
        return kufarAdvertisement.getParameterValueByParameterName("women_clothes_type").isPresent();
    }


}
