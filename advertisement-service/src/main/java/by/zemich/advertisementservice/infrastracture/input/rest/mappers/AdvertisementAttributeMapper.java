package by.zemich.advertisementservice.infrastracture.input.rest.mappers;

import by.zemich.advertisementservice.domain.entity.AdvertisementAttribute;
import by.zemich.advertisementservice.infrastracture.input.rest.data.response.AdvertisementAttributeDto;

import java.util.List;

public class AdvertisementAttributeMapper {
    public static AdvertisementAttributeDto mapToDto(AdvertisementAttribute attribute) {
        return AdvertisementAttributeDto.builder()
                .name(attribute.getCategoryAttribute().name())
                .value(attribute.getValue())
                .build();
    }
}
