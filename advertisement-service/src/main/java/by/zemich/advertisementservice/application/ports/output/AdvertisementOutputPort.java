package by.zemich.advertisementservice.application.ports.output;

import by.zemich.advertisementservice.domain.entity.Advertisement;
import by.zemich.advertisementservice.domain.entity.AdvertisementAttribute;
import by.zemich.advertisementservice.domain.request.Pagination;
import by.zemich.advertisementservice.domain.valueobject.AdvertisementId;
import by.zemich.advertisementservice.domain.valueobject.Id;
import by.zemich.advertisementservice.domain.valueobject.Side;

import java.util.List;
import java.util.Optional;

public interface AdvertisementOutputPort {
    Advertisement saveNew(Advertisement advertisement);
    Advertisement update(Advertisement advertisement);
    void updatePrice(Advertisement advertisement);

    Optional<Advertisement> retrieveById(AdvertisementId id);

    List<Advertisement> retrieveAll(Pagination pagination);

    List<Advertisement> retrieveAllByUserId(Id userId);

    List<Advertisement> retrieveByAttributes(List<AdvertisementAttribute> attributes, Side side);
}
