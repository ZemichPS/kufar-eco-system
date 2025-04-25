package by.zemich.advertisementservice.domain.command;

import by.zemich.advertisementservice.domain.valueobject.AdvertisementId;
import by.zemich.advertisementservice.domain.valueobject.CategoryAttributeId;

import java.util.Map;

public record AddAttributesCommand(
        AdvertisementId advertisementId,
        Map<CategoryAttributeId, String> attributes
) {
}
