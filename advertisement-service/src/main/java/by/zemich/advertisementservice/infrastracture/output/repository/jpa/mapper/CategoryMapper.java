package by.zemich.advertisementservice.infrastracture.output.repository.jpa.mapper;

import by.zemich.advertisementservice.domain.entity.Category;
import by.zemich.advertisementservice.domain.valueobject.Id;
import by.zemich.advertisementservice.infrastracture.output.repository.jpa.entity.CategoryEntity;

import java.util.HashSet;

public class CategoryMapper {
    public static CategoryEntity mapToEntity(Category category) {
        return CategoryEntity.builder()
                .uuid(category.getId().uuid())
                .name(category.getName())
                .attributes(new HashSet<>())
                .build();
    }

    public static Category mapToDomain(CategoryEntity entity){
        return new Category(
                new Id(entity.getUuid()),
                entity.getName()
        );
    }
}
