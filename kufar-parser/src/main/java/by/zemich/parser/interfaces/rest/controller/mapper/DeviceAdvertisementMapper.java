package by.zemich.parser.interfaces.rest.controller.mapper;

import by.zemich.parser.domain.model.Advertisement;
import by.zemich.parser.interfaces.rest.controller.dto.response.DeviceAdvertisementDto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class DeviceAdvertisementMapper {
    public static DeviceAdvertisementDto mapToDto(Advertisement ad) {
        return DeviceAdvertisementDto.builder()
                .id(ad.getId())
                .adId(ad.getAdId())
                .link(ad.getLink())
                .category(ad.getCategory())
                .companyAd(ad.isCompanyAd())
                .currency(ad.getCurrency())
                .publishedAt(ad.getPublishedAt())
                .subject(ad.getSubject())
                .type(ad.getType())
                .priceInByn(ad.getPriceInByn())
                .priceInUsd(ad.getPriceInUsd())
                .details(ad.getDetails())
                .fullyFunctional(ad.isFullyFunctional())
                .brand(ad.getBrand().orElse("undefined"))
                .model(ad.getModel().orElse("undefined"))
                .fullAddress(ad.getFullAddress())
                .condition(ad.getCondition())
                .photoLink(ad.getPhotoLink().orElse("undefined"))
                .build();
    }
}
