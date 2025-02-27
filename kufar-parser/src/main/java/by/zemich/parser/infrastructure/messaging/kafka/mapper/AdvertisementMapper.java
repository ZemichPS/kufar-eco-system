package by.zemich.parser.infrastructure.messaging.kafka.mapper;


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


        return event;
    }

    public static Parameter mapToParameter(Advertisement.Parameter advertisementParameter) {
        return Parameter.newBuilder()
                .setIdentity(advertisementParameter.getIdentity())
                .setValue(advertisementParameter.getValue())
                .setLabel(advertisementParameter.getLabel())
                .build();
    }




}
