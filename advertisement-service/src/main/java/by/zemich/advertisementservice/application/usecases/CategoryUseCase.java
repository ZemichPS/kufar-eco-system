package by.zemich.advertisementservice.application.usecases;

import by.zemich.advertisementservice.domain.entity.Category;
import by.zemich.advertisementservice.domain.valueobject.Id;

import java.util.Collection;
import java.util.List;

public interface CategoryUseCase {
    Id create(String categoryName);
    void deleteById(Id categoryId);

    Category updateById(Id categoryId, String newCategoryName);

    List<Category> getAll();

    Category getById(Id id);

    Id addAttribute(Id categoryId, Id attributeId);
}
