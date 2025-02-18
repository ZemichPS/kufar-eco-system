package by.zemich.advertisementservice.application.ports.output;

import by.zemich.advertisementservice.domain.entity.Advertisement;
import by.zemich.advertisementservice.domain.request.Pagination;
import by.zemich.advertisementservice.domain.valueobject.Id;

import java.util.List;
import java.util.Optional;

public interface AdvertisementOutputPort {
    void persist(Advertisement advertisement);

    Optional<Advertisement> retrieveById(Id id);

    List<Advertisement> retrieveAll(Pagination pagination);

    List<Advertisement> retrieveAllByUserId(Id userId);
}
