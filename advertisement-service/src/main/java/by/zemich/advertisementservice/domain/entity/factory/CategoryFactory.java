package by.zemich.advertisementservice.domain.entity.factory;


import by.zemich.advertisementservice.domain.entity.Category;
import by.zemich.advertisementservice.domain.valueobject.Id;

import java.util.ArrayList;
import java.util.UUID;

public class CategoryFactory {
    public static Category create(String categoryName) {
        return new Category(
                new Id(UUID.randomUUID()),
                categoryName,
                new ArrayList<>()
        );
    }
}
