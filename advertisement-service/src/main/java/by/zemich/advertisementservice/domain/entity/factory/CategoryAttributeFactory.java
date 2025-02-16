package by.zemich.advertisementservice.domain.entity.factory;

import by.zemich.advertisementservice.domain.valueobject.CategoryAttribute;
import by.zemich.advertisementservice.domain.valueobject.Id;

import java.util.UUID;

public class CategoryAttributeFactory {

    public static CategoryAttribute create(String attributeName) {
            return new CategoryAttribute(new Id(UUID.randomUUID()), attributeName);
    }
}
