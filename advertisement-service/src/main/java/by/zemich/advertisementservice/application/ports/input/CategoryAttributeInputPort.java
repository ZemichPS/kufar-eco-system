package by.zemich.advertisementservice.application.ports.input;

import by.zemich.advertisementservice.application.ports.output.CategoryAttributeOutputPort;
import by.zemich.advertisementservice.application.usecases.CategoryAttributeUseCase;
import by.zemich.advertisementservice.domain.entity.factory.CategoryAttributeFactory;
import by.zemich.advertisementservice.domain.exception.CategoryAttributeNotFoundException;
import by.zemich.advertisementservice.domain.valueobject.CategoryAttribute;
import by.zemich.advertisementservice.domain.valueobject.Id;

import java.util.List;

public class CategoryAttributeInputPort implements CategoryAttributeUseCase {

    private final CategoryAttributeOutputPort categoryAttributeOutputPort;

    public CategoryAttributeInputPort(CategoryAttributeOutputPort categoryAttributeOutputPort) {
        this.categoryAttributeOutputPort = categoryAttributeOutputPort;
    }

    @Override
    public Id create(String attributeName) {
        CategoryAttribute attribute = CategoryAttributeFactory.create(attributeName);
        categoryAttributeOutputPort.persist(attribute);
        return attribute.getId();
    }

    @Override
    public void deleteById(Id attributeId) {
        if (!categoryAttributeOutputPort.existsById(attributeId))
            throw new CategoryAttributeNotFoundException(attributeId.uuid().toString());
        if (!categoryAttributeOutputPort.delete(attributeId))
            throw new RuntimeException("Failed to deleteById category attribute");
    }

    @Override
    public CategoryAttribute updateNameById(Id attributeUuid, String attributeName) {
        CategoryAttribute attribute = categoryAttributeOutputPort.getById(attributeUuid);
        if (!attribute.getName().equals(attributeName)) {
            attribute.setName(attributeName);
            categoryAttributeOutputPort.persist(attribute);
        }
        return attribute;
    }

    @Override
    public List<CategoryAttribute> getAll() {
        return categoryAttributeOutputPort.getAll();
    }

    @Override
    public CategoryAttribute getById(Id categoryAttributeId) {
        return categoryAttributeOutputPort.getById(categoryAttributeId);
    }
}
