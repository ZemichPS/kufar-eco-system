package by.zemich.advertisementservice.domain.entity.factory;

import by.zemich.advertisementservice.domain.entity.AdvertisementAttribute;
import by.zemich.advertisementservice.domain.valueobject.CategoryAttribute;
import by.zemich.advertisementservice.domain.valueobject.Id;

import java.util.UUID;

public class AdvertisementAttributeFactory {
    public static AdvertisementAttribute create(CategoryAttribute attribute, String value){
        return new AdvertisementAttribute(
                new Id(UUID.randomUUID()),
                attribute,
                value
        );

    }
}
