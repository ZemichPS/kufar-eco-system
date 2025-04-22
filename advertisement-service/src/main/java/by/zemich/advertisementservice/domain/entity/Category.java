package by.zemich.advertisementservice.domain.entity;


import by.zemich.advertisementservice.domain.valueobject.AdCharacteristics;
import by.zemich.advertisementservice.domain.valueobject.CategoryAttribute;
import by.zemich.advertisementservice.domain.valueobject.CategoryId;
import by.zemich.advertisementservice.domain.valueobject.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter()
@Setter
public class Category {
    private CategoryId id;
    private String name;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private List<CategoryAttribute> attributes;

    public Category(CategoryId id, String name) {
        this.id = id;
        this.name = name;
        this.attributes = new ArrayList<>();
    }

    public List<CategoryAttribute> getAttributes() {
        return List.copyOf(attributes);
    }

    public boolean addAttribute(CategoryAttribute attribute) {
        return attributes.add(attribute);
    }

    public boolean remove(CategoryAttribute attribute) {
        return attributes.remove(attribute);
    }

}

