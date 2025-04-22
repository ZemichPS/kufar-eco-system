package by.zemich.advertisementservice.domain.command;

import by.zemich.advertisementservice.domain.valueobject.CategoryId;

public record DeleteCategoryByIdCommand(CategoryId categoryId) {
}
