package by.zemich.advertisementservice.interfaces.rest.mappers;

import by.zemich.advertisementservice.domain.entity.Advertisement;
import by.zemich.advertisementservice.interfaces.rest.data.response.AdvertisementAttributeDto;
import by.zemich.advertisementservice.interfaces.rest.data.response.AdvertisementDto;

import java.util.List;

public class AdvertisementMapper {

    public static AdvertisementDto mapToDto(Advertisement ad) {
        List<AdvertisementAttributeDto> attributes = ad.getAttributes().stream()
                .map(AdvertisementAttributeMapper::mapToDto)
                .toList();

        return AdvertisementDto.builder()
                .id(ad.getId().uuid())
                .userDto(UserMapper.mapToDto(ad.getUser()))
                .category(ad.getCategory().getName())
                .condition(ad.getCondition().name())
                .publishedAt(ad.getPublishedAt())
                .activatedAt(ad.getActivatedAt())
                .priceInByn(ad.getPrice().priceInByn())
                .comment(ad.getComment().value())
                .fileName(ad.getPhoto().filename())
                .active(ad.isActive())
                .attributes(attributes)
                .build();
    }
}
