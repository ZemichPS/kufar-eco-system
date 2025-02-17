package by.zemich.advertisementservice.interfaces.rest.mappers;

import by.zemich.advertisementservice.domain.entity.AdvertisementAttribute;
import by.zemich.advertisementservice.interfaces.rest.data.response.AdvertisementAttributeDto;

public class AdvertisementAttributeMapper {
    public static AdvertisementAttributeDto mapToDto(AdvertisementAttribute attribute) {
        return AdvertisementAttributeDto.builder()
                .name(attribute.getCategoryAttribute().name())
                .value(attribute.getValue())
                .build();
    }
}
