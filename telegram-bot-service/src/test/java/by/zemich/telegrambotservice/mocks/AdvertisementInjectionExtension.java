package by.zemich.telegrambotservice.mocks;

import by.zemich.telegrambotservice.domain.model.KufarAdvertisement;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestInstancePostProcessor;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

public class AdvertisementInjectionExtension implements TestInstancePostProcessor {

    @Override
    public void postProcessTestInstance(Object testInstance, ExtensionContext context) throws Exception {
        Arrays.stream(testInstance.getClass().getDeclaredFields())
                .filter(field -> field.getType().equals(KufarAdvertisement.class))
                .filter(field -> field.isAnnotationPresent(KufarAdvertisementInjection.class))
                .forEach(field -> {
                    try {
                        extractAdvertisementAndInject(testInstance, field);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    private static void extractAdvertisementAndInject(Object testInstance, Field field) throws IllegalAccessException {
        field.setAccessible(true);
        KufarAdvertisementInjection annotation = field.getAnnotation(KufarAdvertisementInjection.class);
        String category = annotation.category();
        KufarAdvertisement advertisement = KufarAdvertisement.builder()
                .id(UUID.randomUUID())
                .kufarId(1007031387L)
                .link("https://www.kufar.by/item/1007042348")
                .category(category)
                .parentCategory("Электроника")
                .companyAd(false)
                .publishedAt(LocalDateTime.now())
                .subject("Iphone")
                .type("sell")
                .priceInByn(new BigDecimal("1500"))
                .priceInUsd(new BigDecimal("500"))
                .commerceMarketPrice(new BigDecimal("1450"))
                .nonCommerceMarketPrice(new BigDecimal("1470"))
                .details("iPhone 15 pro 128GB натуральный титан, 97%акб")
                .images("iphone.jpg")
                .sellerId(UUID.randomUUID().toString())
                .parameters(new ArrayList<>())
                .build();

        if(!annotation.emptyParam()){
            KufarAdvertisement.Parameter manufacturer =  KufarAdvertisement.Parameter.builder()
                    .label("Производитель")
                    .identity("phones_brand")
                    .value("Apple")
                    .build();

            KufarAdvertisement.Parameter model = KufarAdvertisement.Parameter.builder()
                    .label("Модель")
                    .identity("phones_model")
                    .value("iPhone 15 Pro")
                    .build();
            advertisement.getParameters().add(manufacturer);
            advertisement.getParameters().add(model);
        }
        field.set(testInstance, advertisement);
        field.setAccessible(false);
    }

}
