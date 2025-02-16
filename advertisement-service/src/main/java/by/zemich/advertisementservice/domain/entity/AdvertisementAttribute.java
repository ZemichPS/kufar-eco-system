package by.zemich.advertisementservice.domain.entity;

import by.zemich.advertisementservice.domain.valueobject.CategoryAttribute;
import by.zemich.advertisementservice.domain.valueobject.Id;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AdvertisementAttribute {
    private Id id;
    private CategoryAttribute categoryAttribute; // Какой атрибут заполняем?
    private String value; // Храним как строку, но валидируем по типу

}
