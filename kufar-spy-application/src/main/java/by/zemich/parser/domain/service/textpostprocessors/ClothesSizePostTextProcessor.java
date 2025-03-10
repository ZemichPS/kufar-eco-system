package by.zemich.parser.domain.service.textpostprocessors;

import by.zemich.parser.domain.model.Advertisement;
import by.zemich.parser.domain.service.textpostprocessors.api.PostTextProcessor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(3)
public class ClothesSizePostTextProcessor implements PostTextProcessor {


    @Override
    public String process(Advertisement advertisement) {
        String size = advertisement.getParameterValueByParameterName("women_clothes_size").get();
        size = prepare(size);
        return "▫\uFE0F" + PostTextProcessor.getBoldHtmlStyle(" Размер: ") + size;
    }

    @Override
    public boolean isApplicable(Advertisement advertisement) {
        return advertisement.getParameterValueByParameterName("women_clothes_size").isPresent();
    }


    private String prepare(String source){
        if (source.startsWith(",")) source = source.substring(1)
                .trim()
                .replaceAll(">","")
                .replaceAll("<","");
        return source.trim();
    }
}
