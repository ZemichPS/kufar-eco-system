package by.zemich.advertisementservice.application.ports.input;

import by.zemich.advertisementservice.application.ports.output.CategoryEventOutputPort;
import by.zemich.advertisementservice.application.ports.output.CategoryOutputPort;
import by.zemich.advertisementservice.application.usecases.CategoryUseCase;
import by.zemich.advertisementservice.domain.entity.Category;
import by.zemich.advertisementservice.domain.entity.factory.CategoryFactory;
import by.zemich.advertisementservice.domain.exception.EntityNotFoundException;
import by.zemich.advertisementservice.domain.valueobject.Id;

public class CategoryInputPort implements CategoryUseCase {

    private final CategoryOutputPort categoryOutputPort;
    private final CategoryEventOutputPort categoryEventOutputPort;

    public CategoryInputPort(CategoryOutputPort categoryOutputPort,
                             CategoryEventOutputPort categoryEventOutputPort) {
        this.categoryOutputPort = categoryOutputPort;
        this.categoryEventOutputPort = categoryEventOutputPort;
    }

    @Override
    public Id create(String categoryName) {
        Category createdCategory = CategoryFactory.create(categoryName);
        categoryOutputPort.persist(createdCategory);
        categoryEventOutputPort.publishCategoryCreated(createdCategory);
        return createdCategory.getId();
    }

    @Override
    public void deleteById(Id categoryId) {
        if(!categoryOutputPort.existsById(categoryId)) throw new EntityNotFoundException("Category not found");
        if(!categoryOutputPort.deleteById(categoryId)) throw new RuntimeException("Failed to deleteById category");
    }

    @Override
    public Category updateById(Id categoryId, String categoryName) {
        return null;
    }


}
