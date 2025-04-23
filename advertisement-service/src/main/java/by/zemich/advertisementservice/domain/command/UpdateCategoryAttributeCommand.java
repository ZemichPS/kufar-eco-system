package by.zemich.advertisementservice.domain.command;

import by.zemich.advertisementservice.domain.valueobject.CategoryAttributeId;

public record UpdateCategoryAttributeCommand(CategoryAttributeId categoryAttributeId, String name) {
}
