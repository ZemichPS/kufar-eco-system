package by.zemich.advertisementservice.interfaces.rest.mappers;

import by.zemich.advertisementservice.domain.entity.Advertisement;
import by.zemich.advertisementservice.interfaces.rest.data.response.AdvertisementAttributeDto;
import by.zemich.advertisementservice.interfaces.rest.data.response.AdvertisementResponseDto;

import java.util.List;

public class AdvertisementMapper {

    public static AdvertisementResponseDto mapToDto(Advertisement ad) {
        List<AdvertisementAttributeDto> attributes = ad.getAttributes().stream()
                .map(AdvertisementAttributeMapper::mapToDto)
                .toList();

        return AdvertisementResponseDto.builder()
                .id(ad.getId().uuid())
                .userId(ad.getUserId().uuid())
                .category(ad.getCategoryId().getName())
                .condition(ad.getCondition().getConditionDescription())
                .publishedAt(ad.getPublishedAt())
                .activatedAt(ad.getReactivatedAt())
                .priceInByn(ad.getPrice().priceInByn())
                .comment(ad.getComment().value())
                .fileName(ad.getPhoto().filename())
                .active(ad.isActive())
                .attributes(attributes)
                .build();
    }
}
