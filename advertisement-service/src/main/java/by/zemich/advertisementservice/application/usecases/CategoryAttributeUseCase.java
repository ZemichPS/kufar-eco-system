package by.zemich.advertisementservice.application.usecases;

import by.zemich.advertisementservice.domain.valueobject.CategoryAttribute;
import by.zemich.advertisementservice.domain.valueobject.Id;

import java.util.List;

public interface CategoryAttributeUseCase {
    Id create(String attributeName);

    void deleteById(Id attributeId);

    CategoryAttribute updateNameById(Id attributeUuid, String attributeName);

    List<CategoryAttribute> getAll();

    CategoryAttribute getById(Id id);
}
