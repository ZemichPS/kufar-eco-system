package by.zemich.advertisementservice.application.ports.output;

import by.zemich.advertisementservice.domain.entity.Category;
import by.zemich.advertisementservice.domain.valueobject.CategoryAttribute;
import by.zemich.advertisementservice.domain.valueobject.Id;

import java.util.List;
import java.util.Optional;

public interface CategoryPersistenceOutputPort {
    Category getById(Id id);

    Category persist(Category createdCategory);

    boolean existsById(Id categoryId);

    boolean deleteById(Id id);

    List<Category> getAll();
}
