package by.zemich.advertisementservice.domain.command;

import by.zemich.advertisementservice.domain.valueobject.CategoryAttribute;
import by.zemich.advertisementservice.domain.valueobject.CategoryAttributeId;
import by.zemich.advertisementservice.domain.valueobject.CategoryId;

public record CreateCategoryAttributeCommand(
        CategoryId categoryId,
        CategoryAttributeId categoryAttributeId,
        String categoryAttributeName
) {
}
