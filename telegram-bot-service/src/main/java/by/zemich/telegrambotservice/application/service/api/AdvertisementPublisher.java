package by.zemich.telegrambotservice.application.service.api;


import by.zemich.telegrambotservice.domain.model.KufarAdvertisement;

public interface AdvertisementPublisher {
    boolean publish(KufarAdvertisement kufarAdvertisement);
}
