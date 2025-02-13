package by.zemich.advertisementservice.domain.valueobject;

import java.util.List;

public class AdCharacteristics {
    private List<CategoryAttribute> categoryAttributes;

    public boolean addCharacteristic(CategoryAttribute categoryAttribute) {
        return categoryAttributes.add(categoryAttribute);
    }
}
