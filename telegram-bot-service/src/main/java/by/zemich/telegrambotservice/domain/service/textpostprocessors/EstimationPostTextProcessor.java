package by.zemich.telegrambotservice.domain.service.textpostprocessors;

import by.zemich.telegrambotservice.domain.model.KufarAdvertisement;
import by.zemich.telegrambotservice.domain.service.textpostprocessors.api.PostTextProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EstimationPostTextProcessor implements PostTextProcessor {

    @Override
    public String process(KufarAdvertisement advertisement) {
        String line = "▫️ %s: ".formatted(PostTextProcessor.getBoldHtmlStyle("Оценка состояния"));
        return advertisement.isFullyFunctional() ? line + " ✅" : line + " ⚠️";
    }

    @Override
    public boolean isApplicable(KufarAdvertisement advertisement) {
        return advertisement.getCategory().equalsIgnoreCase("17010");
    }
}
