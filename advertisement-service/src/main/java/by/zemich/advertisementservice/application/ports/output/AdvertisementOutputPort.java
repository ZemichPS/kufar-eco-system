package by.zemich.advertisementservice.application.ports.output;

import by.zemich.advertisementservice.domain.entity.Advertisement;
import by.zemich.advertisementservice.domain.valueobject.AdvertisementId;
import by.zemich.advertisementservice.domain.valueobject.Id;

import java.util.List;
import java.util.Optional;

public interface AdvertisementOutputPort {
    Advertisement saveNew(Advertisement advertisement);

    Advertisement update(Advertisement advertisement);

    void delete(AdvertisementId id);

    Optional<Advertisement> retrieveById(AdvertisementId id);

    boolean existsById(AdvertisementId id);

    List<Advertisement> retrieveAllByUserId(Id userId);

}
