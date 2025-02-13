package by.zemich.advertisementservice.domain.entity;


import by.zemich.advertisementservice.domain.valueobject.AdCharacteristics;
import by.zemich.advertisementservice.domain.valueobject.CategoryAttribute;
import by.zemich.advertisementservice.domain.valueobject.Id;

import java.util.List;

public class Category {
    private Id id;
    private String name;
    private List<CategoryAttribute> categoryAttributes;
}
