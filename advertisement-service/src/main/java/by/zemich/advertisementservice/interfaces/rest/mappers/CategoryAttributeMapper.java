package by.zemich.advertisementservice.interfaces.rest.mappers;

import by.zemich.advertisementservice.domain.valueobject.CategoryAttribute;
import by.zemich.advertisementservice.interfaces.rest.data.response.CategoryAttributeResponseDto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CategoryAttributeMapper {
    public static CategoryAttributeResponseDto toDto(CategoryAttribute attribute) {
        return CategoryAttributeResponseDto.builder()
                .uuid(attribute.id().uuid())
                .name(attribute.name())
                .build();
    }
}
