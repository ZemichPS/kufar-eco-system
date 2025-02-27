package by.zemich.telegrambotservice.domain.service.textpostprocessors;

import by.zemich.telegrambotservice.domain.model.KufarAdvertisement;
import by.zemich.telegrambotservice.domain.service.textpostprocessors.api.PostTextProcessor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
@Order(value = 8)
public class WarrantyPostTextProcessor implements PostTextProcessor {

    private final Pattern WARRANTY_PRESENT_PATTERN = Pattern.compile(
            "гарантия есть|на гарантии|с гарантией|гарантия до",
            Pattern.CASE_INSENSITIVE
    );

    @Override
    public String process(KufarAdvertisement advertisement) {
        String details = advertisement.getDetails();
        return "▫️ %s: ".formatted(PostTextProcessor.getBoldHtmlStyle("Наличие гарантии")) + (warrantyChecker(details) ? "есть": "неизвестно");
    }

    @Override
    public boolean isApplicable(KufarAdvertisement advertisement) {
        return advertisement.getCategory().equalsIgnoreCase("Мобильные телефоны");
    }

    private boolean warrantyChecker(String details) {
        return WARRANTY_PRESENT_PATTERN.matcher(details).find();
    }
}
