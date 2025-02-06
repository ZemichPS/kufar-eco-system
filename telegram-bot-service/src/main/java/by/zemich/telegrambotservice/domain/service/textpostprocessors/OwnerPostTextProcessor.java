package by.zemich.telegrambotservice.domain.service.textpostprocessors;

import by.zemich.telegrambotservice.domain.model.KufarAdvertisement;
import by.zemich.telegrambotservice.domain.service.textpostprocessors.api.PostTextProcessor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(value = 7)
public class OwnerPostTextProcessor implements PostTextProcessor {
    @Override
    public String process(KufarAdvertisement advertisement) {
        String text = PostTextProcessor.getBoldHtmlStyle("Объявление организации");
        return advertisement.isCompanyAd() ? "▫️ %s: да".formatted(text)  : "▫️  %s: нет".formatted(text);
    }

    @Override
    public boolean isApplicable(KufarAdvertisement advertisement) {
        return advertisement.getCategory().equalsIgnoreCase("17010");
    }
}
