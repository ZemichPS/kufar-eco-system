package by.zemich.telegrambotservice.domain.service.textpostprocessors;

import by.zemich.telegrambotservice.domain.model.KufarAdvertisement;
import by.zemich.telegrambotservice.domain.service.textpostprocessors.api.PostTextProcessor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(3)
public class ShoesSizePostTextProcessor implements PostTextProcessor {


    @Override
    public String process(KufarAdvertisement advertisement) {
        String size = advertisement.getParameterValueByParameterName("women_shoes_size").get();
        size = prepare(size);
        return "▫\uFE0F" + PostTextProcessor.getBoldHtmlStyle(" Размер: ") + size;
    }

    @Override
    public boolean isApplicable(KufarAdvertisement advertisement) {
        return advertisement.getParameterValueByParameterName("women_shoes_size").isPresent();
    }

    private String prepare(String source){
        if (source.startsWith(",")) source = source.substring(1)
                .trim()
                .replaceAll(">","")
                .replaceAll("<","");
        return source.trim();
    }
}
