package by.zemich.telegrambotservice.domain.service.textpostprocessors;

import by.zemich.telegrambotservice.domain.model.KufarAdvertisement;
import by.zemich.telegrambotservice.domain.service.EmojiService;
import by.zemich.telegrambotservice.domain.service.textpostprocessors.api.PostTextProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Order(value = 1)
public class HeaderPostTextProcessor implements PostTextProcessor {

    private final EmojiService emojiService;

    @Override
    public String process(KufarAdvertisement advertisement) {
        String header;
        if (advertisement.getBrand().isPresent()) {
            String brand = advertisement.getBrand().orElse("");
            String model = advertisement.getModel().orElse("(%s)".formatted(advertisement.getSubject()));
            header = brand + " " + model;
        } else {
            header = advertisement.getSubject();
        }
        header = PostTextProcessor.getBoldHtmlStyle(header);
        header = PostTextProcessor.getTag(header);
        return emojiService.getByCategory(advertisement.getCategory()) + " %s".formatted(header);
    }

    @Override
    public boolean isApplicable(KufarAdvertisement advertisement) {
        return (advertisement.getBrand().isPresent() && advertisement.getModel().isPresent()) || advertisement.getSubject() != null;
    }

}
