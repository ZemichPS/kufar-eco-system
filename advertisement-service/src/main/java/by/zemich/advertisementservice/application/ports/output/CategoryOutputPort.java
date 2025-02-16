package by.zemich.advertisementservice.application.ports.output;

import by.zemich.advertisementservice.domain.entity.Category;
import by.zemich.advertisementservice.domain.valueobject.CategoryAttribute;
import by.zemich.advertisementservice.domain.valueobject.Id;

import java.util.Optional;

public interface CategoryOutputPort {
    Optional<Category> getById(Id id);

    void persist(Category createdCategory);

    void persist(Id categoryId, CategoryAttribute attribute);

    boolean existsById(Id categoryId);

    boolean deleteById(Id id);
}
