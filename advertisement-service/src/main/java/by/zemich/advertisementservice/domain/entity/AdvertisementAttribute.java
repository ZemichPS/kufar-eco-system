package by.zemich.advertisementservice.domain.entity;

import by.zemich.advertisementservice.domain.valueobject.AdvertisementAttributeId;
import by.zemich.advertisementservice.domain.valueobject.CategoryAttribute;
import by.zemich.advertisementservice.domain.valueobject.CategoryAttributeId;
import by.zemich.advertisementservice.domain.valueobject.Id;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AdvertisementAttribute {
    private AdvertisementAttributeId id;
    private CategoryAttributeId categoryAttributeId;
    private String value;
}
