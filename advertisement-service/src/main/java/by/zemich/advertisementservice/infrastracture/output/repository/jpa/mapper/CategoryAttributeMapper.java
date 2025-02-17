package by.zemich.advertisementservice.infrastracture.output.repository.jpa.mapper;

import by.zemich.advertisementservice.domain.valueobject.CategoryAttribute;
import by.zemich.advertisementservice.domain.valueobject.Id;
import by.zemich.advertisementservice.infrastracture.output.repository.jpa.entity.CategoryAttributeEntity;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CategoryAttributeMapper {
    public static CategoryAttributeEntity mapToEntity(CategoryAttribute attribute) {
        return CategoryAttributeEntity.builder()
                .uuid(attribute.id().uuid())
                .name(attribute.name())
                .build();
    }

    public static CategoryAttribute mapToDomain(CategoryAttributeEntity entity) {
        Id id = new Id(entity.getUuid());
        String name = entity.getName();
        return new CategoryAttribute(id, name);
    }
}
