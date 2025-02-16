package by.zemich.advertisementservice.infrastracture.input.rest.mappers;

import by.zemich.advertisementservice.domain.valueobject.CategoryAttribute;
import by.zemich.advertisementservice.infrastracture.input.rest.data.response.CategoryAttributeResponseDto;
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
