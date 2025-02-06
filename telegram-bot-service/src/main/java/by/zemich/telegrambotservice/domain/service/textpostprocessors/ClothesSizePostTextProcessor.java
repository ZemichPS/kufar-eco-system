package by.zemich.telegrambotservice.domain.service.textpostprocessors;

import by.zemich.telegrambotservice.domain.model.KufarAdvertisement;
import by.zemich.telegrambotservice.domain.service.textpostprocessors.api.PostTextProcessor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(3)
public class ClothesSizePostTextProcessor implements PostTextProcessor {


    @Override
    public String process(KufarAdvertisement kufarAdvertisement) {
        String size = kufarAdvertisement.getParameterValueByParameterName("women_clothes_size").get();
        size = prepare(size);
        return "▫\uFE0F" + PostTextProcessor.getBoldHtmlStyle(" Размер: ") + size;
    }

    @Override
    public boolean isApplicable(KufarAdvertisement kufarAdvertisement) {
        return kufarAdvertisement.getParameterValueByParameterName("women_clothes_size").isPresent();
    }


    private String prepare(String source){
        if (source.startsWith(",")) source = source.substring(1)
                .trim()
                .replaceAll(">","")
                .replaceAll("<","");
        return source.trim();
    }
}
