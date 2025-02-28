package by.zemich.advertisementservice.application.ports.output;

import by.zemich.advertisementservice.domain.entity.Advertisement;
import by.zemich.advertisementservice.domain.entity.User;

import java.util.List;

public interface AdvertisementEventOutputPort {
    void publishAdvertisementCreated(Advertisement advertisement);
    void publishAdvertisementUpdated(Advertisement advertisement);

    void publishAdvertisementPriceChanged(Advertisement ad);

    void publishAdvertisementDeactivate(Advertisement ad);

    void publishPositionFount(List<User> users, Advertisement advertisement);
}
