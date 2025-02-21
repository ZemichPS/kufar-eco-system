package by.zemich.advertisementservice.application.ports.output;

import by.zemich.advertisementservice.domain.valueobject.CategoryAttribute;
import by.zemich.advertisementservice.domain.valueobject.Id;

import java.util.List;

public interface CategoryAttributeOutputPort {
    void persist(CategoryAttribute categoryAttribute);
    boolean existsById(Id categoryAttributeId);
    boolean delete(Id categoryAttributeId);
    List<CategoryAttribute> getAll();
    CategoryAttribute getById(Id categoryAttributeId);
}
