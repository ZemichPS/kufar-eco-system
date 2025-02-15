package by.zemich.advertisementservice.application.usecases;

import by.zemich.advertisementservice.domain.valueobject.*;

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
}
