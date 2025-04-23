package by.zemich.advertisementservice.domain.entity.factory;


import by.zemich.advertisementservice.domain.command.CreateCategoryCommand;
import by.zemich.advertisementservice.domain.entity.Category;
import by.zemich.advertisementservice.domain.valueobject.CategoryId;
import by.zemich.advertisementservice.domain.valueobject.Id;

import java.util.ArrayList;
import java.util.UUID;

public class CategoryFactory {
    public static Category create(CreateCategoryCommand command) {
        return new Category(
                command.categoryId(),
                command.name(),
                new ArrayList<>()
        );
    }
}
