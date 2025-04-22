package by.zemich.advertisementservice.domain.command;

import by.zemich.advertisementservice.domain.valueobject.AdvertisementId;

public record DeactivateAdvertisementCommand(AdvertisementId advertisementId) {
}
