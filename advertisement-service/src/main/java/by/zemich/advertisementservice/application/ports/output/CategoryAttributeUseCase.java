package by.zemich.advertisementservice.application.ports.output;

import by.zemich.advertisementservice.domain.valueobject.Id;

public interface CategoryAttributeUseCase {
    public Id add(Id categoryId, String attributeName);
    public void delete(Id attributeId);
}
