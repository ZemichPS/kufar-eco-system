package by.zemich.advertisementservice.interfaces.rest.mappers;

import by.zemich.advertisementservice.domain.entity.Category;
import by.zemich.advertisementservice.interfaces.rest.data.response.CategoryAttributeResponseDto;
import by.zemich.advertisementservice.interfaces.rest.data.response.CategoryResponseDto;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@UtilityClass
public class CategoryMapper {
    public static CategoryResponseDto mapToDto(Category category) {
        return CategoryResponseDto.builder()
                .uuid(category.getId().uuid())
                .name(category.getName())
                .attributes(new ArrayList<>())
                .build();
    }
}
