package by.zemich.telegrambotservice.domain.service.textpostprocessors;

import by.zemich.telegrambotservice.domain.model.KufarAdvertisement;
import by.zemich.telegrambotservice.domain.service.textpostprocessors.api.PostTextProcessor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(9)
public class ConditionPostTextProcessor implements PostTextProcessor {

    @Override
    public String process(KufarAdvertisement advertisement) {
        return "▫️ %s: ".formatted(PostTextProcessor.getBoldHtmlStyle("Состояние")) + advertisement.getCondition();
    }

    @Override
    public boolean isApplicable(KufarAdvertisement advertisement) {
        return advertisement.getCondition() != null;
    }
}
