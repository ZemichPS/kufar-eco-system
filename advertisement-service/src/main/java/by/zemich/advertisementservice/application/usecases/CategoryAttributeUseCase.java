package by.zemich.advertisementservice.application.usecases;

import by.zemich.advertisementservice.domain.valueobject.CategoryAttribute;
import by.zemich.advertisementservice.domain.valueobject.Id;

public interface CategoryAttributeUseCase {
    Id create(Id categoryId, String attributeName);

    void deleteById(Id attributeId);

    CategoryAttribute updateById(Id attributeUuid, String attributeName);
}
