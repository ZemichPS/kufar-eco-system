package by.zemich.advertisementservice.domain.repository;

import by.zemich.advertisementservice.domain.response.FullAdvertisementDto;
import by.zemich.advertisementservice.domain.valueobject.AdvertisementId;

public interface AdvertisementQueryRepository {
    FullAdvertisementDto getFullResponseById(AdvertisementId advertisementId);
}
