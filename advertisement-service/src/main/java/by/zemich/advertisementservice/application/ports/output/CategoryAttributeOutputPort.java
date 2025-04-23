package by.zemich.advertisementservice.application.ports.output;

import by.zemich.advertisementservice.domain.valueobject.CategoryAttribute;
import by.zemich.advertisementservice.domain.valueobject.CategoryAttributeId;

import java.util.List;
import java.util.Optional;

public interface CategoryAttributeOutputPort {
    void persist(CategoryAttribute categoryAttribute);
    boolean existsById(CategoryAttributeId categoryAttributeId);
    boolean deleteById(CategoryAttributeId categoryAttributeId);
    List<CategoryAttribute> getAll();
    Optional<CategoryAttribute> getById(CategoryAttributeId id);
}
