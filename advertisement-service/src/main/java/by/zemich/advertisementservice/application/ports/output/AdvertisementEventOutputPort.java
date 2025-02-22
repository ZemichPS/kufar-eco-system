package by.zemich.advertisementservice.application.ports.output;

import by.zemich.advertisementservice.domain.entity.Advertisement;

public interface AdvertisementEventOutputPort {
    void publishAdvertisementCreated(Advertisement advertisement);
    void publishAdvertisementUpdated(Advertisement advertisement);

    void publishAdvertisementPriceChanged(Advertisement ad);

    void publishAdvertisementDeactivate(Advertisement ad);
}
