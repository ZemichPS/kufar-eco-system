package by.zemich.advertisementservice.application.ports.input;

import by.zemich.advertisementservice.application.ports.output.CategoryAttributeOutputPort;
import by.zemich.advertisementservice.application.ports.output.CategoryPersistenceOutputPort;
import by.zemich.advertisementservice.application.usecases.CategoryUseCase;
import by.zemich.advertisementservice.domain.entity.Category;
import by.zemich.advertisementservice.domain.entity.factory.CategoryFactory;
import by.zemich.advertisementservice.domain.exception.EntityNotFoundException;
import by.zemich.advertisementservice.domain.valueobject.CategoryAttribute;
import by.zemich.advertisementservice.domain.valueobject.Id;

import java.util.List;

public class CategoryInputPort implements CategoryUseCase {

    private final CategoryPersistenceOutputPort categoryPersistenceOutputPort;
    private final CategoryAttributeOutputPort categoryAttributeOutputPort;

    public CategoryInputPort(CategoryPersistenceOutputPort categoryPersistenceOutputPort,
                             CategoryAttributeOutputPort categoryAttributeOutputPort) {
        this.categoryPersistenceOutputPort = categoryPersistenceOutputPort;
        this.categoryAttributeOutputPort = categoryAttributeOutputPort;
    }

    @Override
    public Id create(String categoryName) {
        Category createdCategory = CategoryFactory.create(categoryName);
        categoryPersistenceOutputPort.persist(createdCategory);
        return createdCategory.getId();
    }

    @Override
    public void deleteById(Id categoryId) {
        if (!categoryPersistenceOutputPort.existsById(categoryId))
            throw new EntityNotFoundException("Category not found");
        if (!categoryPersistenceOutputPort.deleteById(categoryId))
            throw new RuntimeException("Failed to deleteById category");
    }

    @Override
    public Category updateById(Id categoryId, String newCategoryName) {
        Category category = categoryPersistenceOutputPort.getById(categoryId);
        category.setName(newCategoryName);
        return categoryPersistenceOutputPort.persist(category);
    }

    @Override
    public List<Category> getAll() {
        return categoryPersistenceOutputPort.getAll();
    }

    @Override
    public Category getById(Id categoryId) {
        return categoryPersistenceOutputPort.getById(categoryId);
    }

    @Override
    public Id addAttribute(Id categoryId, Id attributeId) {
        Category category = categoryPersistenceOutputPort.getById(categoryId);
        CategoryAttribute categoryAttribute = categoryAttributeOutputPort.getById(attributeId);
        category.addAttribute(categoryAttribute);
        categoryPersistenceOutputPort.persist(category);
        return category.getId();
    }


}
