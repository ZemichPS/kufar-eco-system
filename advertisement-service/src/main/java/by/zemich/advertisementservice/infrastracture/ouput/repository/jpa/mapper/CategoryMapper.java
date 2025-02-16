package by.zemich.advertisementservice.infrastracture.ouput.repository.jpa.mapper;

import by.zemich.advertisementservice.domain.entity.Category;
import by.zemich.advertisementservice.domain.valueobject.Id;
import by.zemich.advertisementservice.infrastracture.ouput.repository.jpa.entity.CategoryEntity;

public class CategoryMapper {
    public static CategoryEntity toEntity(Category category) {
        return CategoryEntity.builder()
                .uuid(category.getId().uuid())
                .name(category.getName())
                .build();
    }

    public static Category toDomain(CategoryEntity entity){
        return new Category(
                new Id(entity.getUuid()),
                entity.getName()
        );
    }
}
