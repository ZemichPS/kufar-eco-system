package by.zemich.parser.domain.service.textpostprocessors;

import by.zemich.parser.domain.model.Advertisement;
import by.zemich.parser.domain.service.textpostprocessors.api.PostTextProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Order(value = 3)
public class EmoutOfMemoryPostTextProcessor implements PostTextProcessor {

    @Override
    public String process(Advertisement advertisement) {
        String memoryAmount = advertisement.getParameterValueByParameterName("phablet_phones_memory")
                .orElse("");
        return memoryAmount.isEmpty() ? "" : "▫️ %s: ".formatted(PostTextProcessor.getBoldHtmlStyle("Объём памяти")) + memoryAmount;
    }

    @Override
    public boolean isApplicable(Advertisement advertisement) {
        return advertisement.getParameterValueByParameterName("phablet_phones_memory").isPresent();
    }
}
