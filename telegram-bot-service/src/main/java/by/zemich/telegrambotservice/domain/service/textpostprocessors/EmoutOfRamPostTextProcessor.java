package by.zemich.telegrambotservice.domain.service.textpostprocessors;

import by.zemich.telegrambotservice.domain.model.KufarAdvertisement;
import by.zemich.telegrambotservice.domain.service.textpostprocessors.api.PostTextProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Order(value = 4)
public class EmoutOfRamPostTextProcessor implements PostTextProcessor {

    @Override
    public String process(KufarAdvertisement advertisement) {

    String ramAmount = advertisement.getParameterValueByParameterName("phablet_phones_ram")
                .orElse("");
        return ramAmount.isEmpty() ? "" : "▫️ %s: ".formatted(PostTextProcessor.getBoldHtmlStyle("Объём ОЗУ")) + ramAmount;
    }

    @Override
    public boolean isApplicable(KufarAdvertisement advertisement) {
        return advertisement.getParameterValueByParameterName("phablet_phones_ram").isPresent();
    }
}
