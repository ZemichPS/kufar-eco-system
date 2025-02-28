package by.zemich.advertisementservice.application.ports.output;

import by.zemich.advertisementservice.domain.entity.Advertisement;
import by.zemich.advertisementservice.domain.entity.AdvertisementAttribute;
import by.zemich.advertisementservice.domain.request.Pagination;
import by.zemich.advertisementservice.domain.valueobject.Id;
import by.zemich.advertisementservice.domain.valueobject.Side;

import java.util.List;

public interface AdvertisementOutputPort {
    void saveNew(Advertisement advertisement);
    void update(Advertisement advertisement);
    void updatePrice(Advertisement advertisement);

    Advertisement retrieveById(Id id);

    List<Advertisement> retrieveAll(Pagination pagination);

    List<Advertisement> retrieveAllByUserId(Id userId);

    List<Advertisement> retrieveByAttributes(List<AdvertisementAttribute> attributes, Side side);
}
