package by.zemich.advertisementservice.interfaces.rest.mappers;

import by.zemich.advertisementservice.domain.entity.AdvertisementAttribute;
import by.zemich.advertisementservice.interfaces.rest.data.response.AdvertisementAttributeDto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class AdvertisementAttributeMapper {

    public static AdvertisementAttributeDto mapToDto(AdvertisementAttribute attribute) {
        return AdvertisementAttributeDto.builder()
                .attributeUuid(attribute.getCategoryAttributeId().uuid())
                .value(attribute.getValue())
                .build();
    }
}
