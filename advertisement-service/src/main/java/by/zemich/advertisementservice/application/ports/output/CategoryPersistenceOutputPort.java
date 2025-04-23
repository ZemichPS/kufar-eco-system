package by.zemich.advertisementservice.application.ports.output;

import by.zemich.advertisementservice.domain.entity.Category;
import by.zemich.advertisementservice.domain.valueobject.CategoryId;
import by.zemich.advertisementservice.domain.valueobject.Id;

import java.util.List;
import java.util.Optional;

public interface CategoryPersistenceOutputPort {
    Optional<Category> getById(CategoryId id);

    Category persist(Category createdCategory);

    boolean existsById(Id categoryId);

    boolean deleteById(CategoryId id);

    List<Category> getAll();
}
