package by.zemich.advertisementservice.domain.valueobject;

import by.zemich.advertisementservice.domain.entity.Category;

public record CategoryAttribute(Id id,
                                String name // Название характеристики (например, "Объём памяти")
) {
}
