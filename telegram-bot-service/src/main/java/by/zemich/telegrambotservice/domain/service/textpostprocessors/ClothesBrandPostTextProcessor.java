package by.zemich.telegrambotservice.domain.service.textpostprocessors;

import by.zemich.telegrambotservice.domain.model.KufarAdvertisement;
import by.zemich.telegrambotservice.domain.service.textpostprocessors.api.PostTextProcessor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(3)
public class ClothesBrandPostTextProcessor implements PostTextProcessor {


    @Override
    public String process(KufarAdvertisement kufarAdvertisement) {
        String brand = kufarAdvertisement.getParameterValueByParameterName("women_clothes_brand").get();
        return "▫\uFE0F" + PostTextProcessor.getBoldHtmlStyle(" Бренд: ") + PostTextProcessor.getTag(brand);
    }

    @Override
    public boolean isApplicable(KufarAdvertisement kufarAdvertisement) {
        return kufarAdvertisement.getParameterValueByParameterName("women_clothes_brand").isPresent();
    }
}
