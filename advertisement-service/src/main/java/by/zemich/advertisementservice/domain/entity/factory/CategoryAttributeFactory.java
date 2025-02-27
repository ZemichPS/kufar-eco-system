package by.zemich.advertisementservice.domain.entity.factory;

import by.zemich.advertisementservice.domain.entity.AdvertisementAttribute;
import by.zemich.advertisementservice.domain.entity.Category;
import by.zemich.advertisementservice.domain.valueobject.CategoryAttribute;
import by.zemich.advertisementservice.domain.valueobject.Id;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class CategoryAttributeFactory {

    public static CategoryAttribute get(String attributeName) {
        return new CategoryAttribute(new Id(UUID.randomUUID()), attributeName);
    }


}
