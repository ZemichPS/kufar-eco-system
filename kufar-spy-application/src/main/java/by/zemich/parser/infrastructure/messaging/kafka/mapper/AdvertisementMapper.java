package by.zemich.parser.infrastructure.messaging.kafka.mapper;


import by.zemich.parser.domain.model.Advertisement;
import by.zemich.parser.domain.model.events.AdvertisementCreatedEvent;
import by.zemich.parser.domain.model.events.Parameter;

import java.time.ZoneOffset;
import java.util.List;

public class AdvertisementMapper {

    public static AdvertisementCreatedEvent mapToEvent(Advertisement advertisement) {
        List<Parameter> parameters = advertisement.getParameters().stream()
                .map(AdvertisementMapper::mapToParameter)
                .toList();

        return AdvertisementCreatedEvent.newBuilder()
                .setId(advertisement.getId())
                .setKufarId(advertisement.getAdId())
                .setLink(advertisement.getLink())
                .setCategory(advertisement.getCategory())
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

    }

    public static Parameter mapToParameter(Advertisement.Parameter advertisementParameter) {
        return Parameter.newBuilder()
                .setIdentity(advertisementParameter.getIdentity())
                .setValue(advertisementParameter.getValue())
                .setLabel(advertisementParameter.getLabel())
                .build();
    }
}
