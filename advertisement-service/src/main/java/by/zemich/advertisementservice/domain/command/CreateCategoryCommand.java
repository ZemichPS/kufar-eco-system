package by.zemich.advertisementservice.domain.command;

import by.zemich.advertisementservice.domain.entity.Category;
import by.zemich.advertisementservice.domain.valueobject.CategoryId;

public record CreateCategoryCommand(CategoryId categoryId, String name) {

}
