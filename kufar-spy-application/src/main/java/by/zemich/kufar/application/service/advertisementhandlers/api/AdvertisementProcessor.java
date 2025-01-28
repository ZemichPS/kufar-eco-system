package by.zemich.kufar.application.service.advertisementhandlers.api;

import by.zemich.kufar.domain.model.Advertisement;

public interface AdvertisementProcessor {
    void process(Advertisement advertisement);

    boolean canProcess(Advertisement advertisement);
}
