package by.zemich.advertisementservice.domain.command;

import by.zemich.advertisementservice.domain.valueobject.AdvertisementId;

public record DeleteAdvertisementCommand(AdvertisementId advertisementId) {
}
