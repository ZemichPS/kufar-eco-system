package by.zemich.advertisementservice.application.ports.input;

import by.zemich.advertisementservice.application.ports.output.CategoryAttributeOutputPort;
import by.zemich.advertisementservice.application.ports.output.CategoryPersistenceOutputPort;
import by.zemich.advertisementservice.application.usecases.CommandCategoryUseCase;
import by.zemich.advertisementservice.domain.command.CreateCategoryCommand;
import by.zemich.advertisementservice.domain.command.DeleteCategoryByIdCommand;
import by.zemich.advertisementservice.domain.command.UpdateBuIdCategoryCommand;
import by.zemich.advertisementservice.domain.entity.Category;
import by.zemich.advertisementservice.domain.entity.factory.CategoryFactory;
import by.zemich.advertisementservice.domain.exception.EntityNotFoundException;
import by.zemich.advertisementservice.domain.valueobject.CategoryAttribute;
import by.zemich.advertisementservice.domain.valueobject.CategoryId;
import by.zemich.advertisementservice.domain.valueobject.Id;

import java.util.List;

public class CommandCategoryInputPort implements CommandCategoryUseCase {

    private final CategoryPersistenceOutputPort categoryPersistenceOutputPort;
    private final CategoryAttributeOutputPort categoryAttributeOutputPort;

    public CommandCategoryInputPort(CategoryPersistenceOutputPort categoryPersistenceOutputPort,
                                    CategoryAttributeOutputPort categoryAttributeOutputPort) {
        this.categoryPersistenceOutputPort = categoryPersistenceOutputPort;
        this.categoryAttributeOutputPort = categoryAttributeOutputPort;
    }

    @Override
    public CategoryId handle(CreateCategoryCommand command) {
        Category createdCategory = CategoryFactory.create(command);
        categoryPersistenceOutputPort.persist(createdCategory);
        return createdCategory.getId();
    }

    @Override
    public void handle(DeleteCategoryByIdCommand command) {
        categoryPersistenceOutputPort.deleteById(command.categoryId());
    }

    @Override
    public Category handle(UpdateBuIdCategoryCommand command) {
        Category category = categoryPersistenceOutputPort.getById(command.categoryId());
        category.setName(category.getName());
        categoryPersistenceOutputPort.persist(category);
        return category;
    }



}
