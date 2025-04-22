package by.zemich.advertisementservice.domain.command;

import by.zemich.advertisementservice.domain.valueobject.AdvertisementId;
import by.zemich.advertisementservice.domain.valueobject.Price;

public record UpdateAdvertisementPriceCommand(AdvertisementId advertisementId, Price price) {
}
