package by.zemich.parser.infrastructure.messaging.kafka.mapper;


import by.zemich.parser.application.service.CategoryService;
import by.zemich.parser.application.service.SubCategoryService;
import by.zemich.parser.domain.model.Advertisement;
import by.zemich.parser.domain.model.Category;
import by.zemich.parser.domain.model.events.AdvertisementCreatedEvent;
import by.zemich.parser.domain.model.events.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.ZoneOffset;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AdvertisementMapper {

    private final CategoryService categoryService;
    private final SubCategoryService subCategoryService;

    public AdvertisementCreatedEvent mapToEvent(Advertisement advertisement) {
        List<Parameter> parameters = advertisement.getParameters().stream()
                .map(AdvertisementMapper::mapToParameter)
                .toList();


        AdvertisementCreatedEvent event = AdvertisementCreatedEvent.newBuilder()
                .setId(advertisement.getId())
                .setKufarId(advertisement.getAdId())
                .setLink(advertisement.getLink())
                .setCommerce(advertisement.isCompanyAd())
                .setPublishedAt(advertisement.getPublishedAt().toInstant(ZoneOffset.UTC))
                .setSubject(advertisement.getSubject())
                .setType(advertisement.getType())
                .setPriceInByn(advertisement.getPriceInByn())
                .setPriceInUsd(advertisement.getPriceInUsd())
                .setDetails(advertisement.getDetails())
                .setSellerId(advertisement.getSeller().getId())
                .setFullyFunctional(advertisement.isFullyFunctional())
                .setImages(advertisement.getImages())
                .setParameters(parameters)
                .build();

        String categoryId = advertisement.getCategory();
        setCategory(event, categoryId);
        return event;
    }

    public static Parameter mapToParameter(Advertisement.Parameter advertisementParameter) {
        return Parameter.newBuilder()
                .setIdentity(advertisementParameter.getIdentity())
                .setValue(advertisementParameter.getValue())
                .setLabel(advertisementParameter.getLabel())
                .build();
    }

    private void setCategory(AdvertisementCreatedEvent event, String categoryId) {

        subCategoryService.getById(categoryId).ifPresentOrElse(
                subcategory -> {
                    Category category = subcategory.getCategory();
                    event.setCategory(subcategory.getName());
                    event.setParentCategory(category.getName());
                }, () -> {
                    event.setCategory("Unknown");
                    event.setParentCategory("Unknown");
                });


    }
}
