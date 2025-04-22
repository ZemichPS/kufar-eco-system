package by.zemich.advertisementservice.domain.valueobject;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryAttribute {
    private CategoryAttributeId id;
    private String name;

    public CategoryAttribute(CategoryAttributeId id, String name) {
        this.id = id;
        this.name = name;
    }

}
