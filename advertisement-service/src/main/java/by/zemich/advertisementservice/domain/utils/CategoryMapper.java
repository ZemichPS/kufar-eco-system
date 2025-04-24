package by.zemich.advertisementservice.domain.utils;

import by.zemich.advertisementservice.domain.entity.Category;
import by.zemich.advertisementservice.domain.dto.CategoryAttributeDto;
import by.zemich.advertisementservice.domain.dto.CategoryFullDto;
import by.zemich.advertisementservice.domain.valueobject.CategoryAttribute;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CategoryMapper {

    public static CategoryFullDto map(Category category) {
        return CategoryFullDto.builder()
                .id(category.getId())
                .name(category.getName())
                .attributes(category.getAttributes().stream().map(attribute -> CategoryAttributeDto.builder()
                        .uuid(category.getId().uuid())
                        .name(attribute.getName())
                        .build()).toList())
                .build();
    }

    CategoryAttributeDto map(CategoryAttribute categoryAttribute) {
        return CategoryAttributeDto.builder()
                .uuid(categoryAttribute.getId().uuid())
                .name(categoryAttribute.getName())
                .build();
    }

}
