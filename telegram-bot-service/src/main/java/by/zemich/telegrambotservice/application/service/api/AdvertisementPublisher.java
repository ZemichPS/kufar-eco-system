package by.zemich.telegrambotservice.application.service.api;


import by.zemich.telegrambotservice.domain.model.Advertisement;

public interface AdvertisementPublisher {
    boolean publish(Advertisement advertisement);
}
