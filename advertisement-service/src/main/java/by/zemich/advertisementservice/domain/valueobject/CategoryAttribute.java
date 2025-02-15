package by.zemich.advertisementservice.domain.valueobject;

import by.zemich.advertisementservice.domain.entity.Category;

public class CategoryAttribute {
    private Id id;
    private String name; // Название характеристики (например, "Объём памяти")
    private AttributeType type; // STRING, NUMBER, BOOLEAN, ENUM
    private Category category;
}
