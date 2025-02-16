package by.zemich.advertisementservice.application.usecases;

import by.zemich.advertisementservice.domain.entity.Advertisement;
import by.zemich.advertisementservice.domain.request.Pagination;
import by.zemich.advertisementservice.domain.valueobject.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface AdvertisementUseCase {
    Id create(
            Id userId,
            Id categoryId,
            Condition condition,
            Price price,
            Comment comment,
            Photo photo,
            Map<UUID, String> attributesMap
    );

    Advertisement updatePriceById(Id advertisementId, Price price);

    Advertisement getById(Id advertisementId);

    Advertisement activate(Id advertisementId);

    Advertisement deactivate(Id advertisementId);

    List<Advertisement> getAllActive(Pagination pagination);

    List<Advertisement> getAll(Pagination pagination);

    List<Advertisement> getAllByUserId(Id userId);
}
