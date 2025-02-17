package by.zemich.advertisementservice.infrastracture.output.repository.jpa.mapper;

import by.zemich.advertisementservice.domain.entity.AdvertisementAttribute;
import by.zemich.advertisementservice.infrastracture.output.repository.jpa.entity.AdvertisementAttributeEntity;
import by.zemich.advertisementservice.interfaces.rest.data.response.AdvertisementAttributeDto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class AdvertisementAttributeMapper {
    public AdvertisementAttributeEntity mapToEntity(AdvertisementAttribute attribute) {
        return AdvertisementAttributeEntity.builder()
                .uuid(attribute.getId().uuid())
                .value(attribute.getValue())
                .build();
    }
}
