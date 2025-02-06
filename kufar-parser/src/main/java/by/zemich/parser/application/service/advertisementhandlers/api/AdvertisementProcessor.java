package by.zemich.parser.application.service.advertisementhandlers.api;

import by.zemich.parser.domain.model.Advertisement;

public interface AdvertisementProcessor {
    void process(Advertisement advertisement);

    boolean canProcess(Advertisement advertisement);
}
