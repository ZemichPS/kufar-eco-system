package by.zemich.advertisementservice.application.usecases;

import by.zemich.advertisementservice.domain.command.*;
import by.zemich.advertisementservice.domain.valueobject.AdvertisementId;

public interface AdvertisementCommandUseCases {
    AdvertisementId handle(CreateAdvertisementCommand command);

    void handle(UpdateAdvertisementCommand command);

    void handle(AddAttributesCommand command);

    void handle(UpdateAdvertisementPriceCommand command);

    void handle(ActivateAdvertisementCommand command);

    void handle(DeactivateAdvertisementCommand command);
}
