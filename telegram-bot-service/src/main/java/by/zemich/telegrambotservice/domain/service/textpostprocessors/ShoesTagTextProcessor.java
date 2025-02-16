package by.zemich.telegrambotservice.domain.service.textpostprocessors;

import by.zemich.telegrambotservice.domain.model.KufarAdvertisement;
import by.zemich.telegrambotservice.domain.service.textpostprocessors.api.PostTextProcessor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(12)
public class ShoesTagTextProcessor implements PostTextProcessor {
    @Override
    public String process(KufarAdvertisement advertisement) {
        String value = advertisement.getParameterValueByParameterName("women_shoes_type").get();
        if (value.isEmpty() || value.isBlank()) return "";
        return PostTextProcessor.getTag(value);
    }

    @Override
    public boolean isApplicable(KufarAdvertisement advertisement) {
        return advertisement.getParameterValueByParameterName("women_shoes_type").isPresent();
    }
}
