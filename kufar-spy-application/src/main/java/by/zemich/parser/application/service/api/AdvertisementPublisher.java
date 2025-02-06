package by.zemich.parser.application.service.api;

import by.zemich.parser.domain.model.Advertisement;

public interface AdvertisementPublisher {
    boolean publish(Advertisement advertisement);
}
