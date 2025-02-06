package by.zemich.telegrambotservice.domain.service.textpostprocessors;

import by.zemich.telegrambotservice.domain.model.KufarAdvertisement;
import by.zemich.telegrambotservice.domain.service.textpostprocessors.api.PostTextProcessor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(value = 5)
public class LocationPostTextProcessor implements PostTextProcessor {
    @Override
    public String process(KufarAdvertisement advertisement) {
        return "\uD83C\uDF0D %s: ".formatted(PostTextProcessor.getBoldHtmlStyle("Адрес")) + advertisement.getFullAddress();
    }

    @Override
    public boolean isApplicable(KufarAdvertisement advertisement) {
        return true;
    }
}
