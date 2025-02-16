package by.zemich.advertisementservice.infrastracture.input.rest.mappers;

import by.zemich.advertisementservice.domain.entity.Category;
import by.zemich.advertisementservice.infrastracture.input.rest.data.response.CategoryAttributeResponseDto;
import by.zemich.advertisementservice.infrastracture.input.rest.data.response.CategoryResponseDto;
import lombok.experimental.UtilityClass;

import java.util.List;


@UtilityClass
public class CategoryMapper {
    public static CategoryResponseDto toDto(Category category) {
        List<CategoryAttributeResponseDto> attributesResponse = category.getAttributes().stream()
                .map(attribute -> {
                    return CategoryAttributeResponseDto.builder()
                            .uuid(attribute.id().uuid())
                            .name(attribute.name())
                            .build();
                }).toList();

        return CategoryResponseDto.builder()
                .uuid(category.getId().uuid())
                .name(category.getName())
                .attributes(attributesResponse)
                .build();
    }
}
