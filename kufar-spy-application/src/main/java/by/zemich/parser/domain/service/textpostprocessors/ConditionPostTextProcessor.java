package by.zemich.parser.domain.service.textpostprocessors;

import by.zemich.parser.domain.model.Advertisement;
import by.zemich.parser.domain.service.textpostprocessors.api.PostTextProcessor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(9)
public class ConditionPostTextProcessor implements PostTextProcessor {

    @Override
    public String process(Advertisement advertisement) {
        return "▫️ %s: ".formatted(PostTextProcessor.getBoldHtmlStyle("Состояние")) + advertisement.getCondition();
    }

    @Override
    public boolean isApplicable(Advertisement advertisement) {
        return true;
    }
}
