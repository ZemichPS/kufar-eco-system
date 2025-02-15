package by.zemich.advertisementservice.application.usecases;

import by.zemich.advertisementservice.domain.valueobject.CategoryAttribute;

public interface CategoryAttributeUseCase {
    void persist(CategoryAttribute categoryAttribute);
}
