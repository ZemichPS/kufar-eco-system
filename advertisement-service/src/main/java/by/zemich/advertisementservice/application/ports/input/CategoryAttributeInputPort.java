package by.zemich.advertisementservice.application.ports.input;

import by.zemich.advertisementservice.application.ports.output.CategoryAttributeOutputPort;
import by.zemich.advertisementservice.application.ports.output.CategoryPersistenceOutputPort;
import by.zemich.advertisementservice.application.usecases.CategoryAttributeUseCase;
import by.zemich.advertisementservice.domain.command.CreateCategoryAttributeCommand;
import by.zemich.advertisementservice.domain.command.DeleteCategoryAttributeCommand;
import by.zemich.advertisementservice.domain.command.UpdateCategoryAttributeCommand;
import by.zemich.advertisementservice.domain.entity.Category;
import by.zemich.advertisementservice.domain.exception.CategoryAttributeNotFoundException;
import by.zemich.advertisementservice.domain.valueobject.CategoryAttribute;
import by.zemich.advertisementservice.domain.valueobject.CategoryAttributeId;
import by.zemich.advertisementservice.domain.valueobject.CategoryId;
import by.zemich.advertisementservice.domain.valueobject.Id;

import java.util.List;

public class CategoryAttributeInputPort implements CategoryAttributeUseCase {

    private final CategoryAttributeOutputPort categoryAttributeOutputPort;
    private final CategoryPersistenceOutputPort categoryPersistenceOutputPort;

    public CategoryAttributeInputPort(CategoryAttributeOutputPort categoryAttributeOutputPort, CategoryPersistenceOutputPort categoryPersistenceOutputPort) {
        this.categoryAttributeOutputPort = categoryAttributeOutputPort;
        this.categoryPersistenceOutputPort = categoryPersistenceOutputPort;
    }

    @Override
    public void handle(DeleteCategoryAttributeCommand command) {
        CategoryAttributeId attributeId = command.categoryAttributeId();
        if (!categoryAttributeOutputPort.existsById(attributeId)) {
            throw new CategoryAttributeNotFoundException(attributeId.uuid().toString());
        }
        categoryAttributeOutputPort.deleteById(attributeId);
    }

    @Override
    public List<CategoryAttribute> getAll() {
        return categoryAttributeOutputPort.getAll();
    }

    @Override
    public CategoryAttribute getById(Id categoryAttributeId) {
        return categoryAttributeOutputPort.getById(categoryAttributeId);
    }

    @Override
    public CategoryAttributeId handle(CreateCategoryAttributeCommand command) {
        CategoryId categoryId = command.categoryId();
        Category category = categoryPersistenceOutputPort.getById(categoryId)
                .orElseThrow(() -> new CategoryAttributeNotFoundException(categoryId.uuid().toString()));

        CategoryAttribute newAttribute = new CategoryAttribute(
                command.categoryAttributeId(),
                command.categoryAttributeName()
        );
        category.addAttribute(newAttribute);
        categoryPersistenceOutputPort.persist(category);
        return newAttribute.getId();
    }

    @Override
    public void handle(UpdateCategoryAttributeCommand command) {
        CategoryAttribute attribute = categoryAttributeOutputPort.getById(command.categoryAttributeId())
                .orElseThrow(() -> new CategoryAttributeNotFoundException(command.categoryAttributeId().uuid().toString()));
        attribute.setName(command.name());
        categoryAttributeOutputPort.persist(attribute);
    }


}
