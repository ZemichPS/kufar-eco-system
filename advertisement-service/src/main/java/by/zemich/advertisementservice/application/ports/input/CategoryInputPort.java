package by.zemich.advertisementservice.application.ports.input;

import by.zemich.advertisementservice.application.ports.output.CategoryPersistenceOutputPort;
import by.zemich.advertisementservice.application.usecases.CategoryUseCase;
import by.zemich.advertisementservice.domain.entity.Category;
import by.zemich.advertisementservice.domain.entity.factory.CategoryFactory;
import by.zemich.advertisementservice.domain.exception.EntityNotFoundException;
import by.zemich.advertisementservice.domain.valueobject.Id;

import java.util.List;

public class CategoryInputPort implements CategoryUseCase {

    private final CategoryPersistenceOutputPort categoryPersistenceOutputPort;

    public CategoryInputPort(CategoryPersistenceOutputPort categoryPersistenceOutputPort) {
        this.categoryPersistenceOutputPort = categoryPersistenceOutputPort;
    }

    @Override
    public Id create(String categoryName) {
        Category createdCategory = CategoryFactory.create(categoryName);
        categoryPersistenceOutputPort.persist(createdCategory);
        return createdCategory.getId();
    }

    @Override
    public void deleteById(Id categoryId) {
        if (!categoryPersistenceOutputPort.existsById(categoryId)) throw new EntityNotFoundException("Category not found");
        if (!categoryPersistenceOutputPort.deleteById(categoryId)) throw new RuntimeException("Failed to deleteById category");
    }

    @Override
    public Category updateById(Id categoryId, String newCategoryName) {
        Category category = categoryPersistenceOutputPort.getById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));
        category.setName(newCategoryName);
        return categoryPersistenceOutputPort.persist(category);
    }

    @Override
    public List<Category> getAll() {
        return categoryPersistenceOutputPort.getAll();
    }


}
