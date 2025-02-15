package by.zemich.advertisementservice.domain.entity;

import by.zemich.advertisementservice.domain.valueobject.CategoryAttribute;
import by.zemich.advertisementservice.domain.valueobject.Id;

public class AdvertisementAttribute {
    private Id id;
    private Advertisement advertisement;
    private CategoryAttribute categoryAttribute; // Какой атрибут заполняем?
    private String value; // Храним как строку, но валидируем по типу
}
