package by.zemich.advertisementservice.application.ports.output;

import by.zemich.advertisementservice.domain.valueobject.CategoryAttribute;
import by.zemich.advertisementservice.domain.valueobject.Id;

public interface CategoryAttributeOutputPort {
    void persist(Id categoryId, CategoryAttribute categoryAttribute);
    boolean existsById(Id categoryAttributeId);
    boolean delete(Id categoryAttributeId);
}
