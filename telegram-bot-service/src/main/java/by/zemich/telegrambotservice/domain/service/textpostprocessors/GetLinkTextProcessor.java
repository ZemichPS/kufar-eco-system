package by.zemich.telegrambotservice.domain.service.textpostprocessors;

import by.zemich.telegrambotservice.domain.model.KufarAdvertisement;
import by.zemich.telegrambotservice.domain.service.textpostprocessors.api.PostTextProcessor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(12)
public class GetLinkTextProcessor implements PostTextProcessor {
    @Override
    public String process(KufarAdvertisement advertisement) {
        return PostTextProcessor.getHtmlLink(advertisement.getLink(), "ссылка на страницу товара kufar");
    }

    @Override
    public boolean isApplicable(KufarAdvertisement advertisement) {
        return true;
    }


}
