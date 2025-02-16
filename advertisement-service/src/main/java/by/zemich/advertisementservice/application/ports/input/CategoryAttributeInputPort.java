package by.zemich.advertisementservice.application.ports.input;

import by.zemich.advertisementservice.application.ports.output.CategoryAttributeOutputPort;
import by.zemich.advertisementservice.application.ports.output.CategoryAttributeUseCase;
import by.zemich.advertisementservice.domain.entity.factory.CategoryAttributeFactory;
import by.zemich.advertisementservice.domain.exception.EntityNotFoundException;
import by.zemich.advertisementservice.domain.valueobject.CategoryAttribute;
import by.zemich.advertisementservice.domain.valueobject.Id;

public class CategoryAttributeInputPort implements CategoryAttributeUseCase {

    private final CategoryAttributeOutputPort categoryAttributeOutputPort;

    public CategoryAttributeInputPort(CategoryAttributeOutputPort categoryAttributeOutputPort) {
        this.categoryAttributeOutputPort = categoryAttributeOutputPort;
    }

    @Override
    public Id add(Id categoryId, String attributeName) {
        CategoryAttribute attribute = CategoryAttributeFactory.create(attributeName);
        categoryAttributeOutputPort.persist(categoryId, attribute);
        return attribute.id();
    }

    @Override
    public void delete(Id attributeId) {
        if (!categoryAttributeOutputPort.existsById(attributeId))
            throw new EntityNotFoundException("CategoryAttribute does not exist");
        if (!categoryAttributeOutputPort.delete(attributeId))
            throw new RuntimeException("Failed to delete category attribute");
    }
}
