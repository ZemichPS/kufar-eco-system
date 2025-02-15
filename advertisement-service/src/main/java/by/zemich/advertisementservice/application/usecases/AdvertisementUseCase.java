package by.zemich.advertisementservice.application.usecases;

import by.zemich.advertisementservice.domain.entity.Advertisement;

public interface AdvertisementUseCase {
    void persist(Advertisement advertisement);
}
