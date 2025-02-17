package by.zemich.advertisementservice.domain.entity.factory;

import by.zemich.advertisementservice.domain.entity.Advertisement;
import by.zemich.advertisementservice.domain.entity.Category;
import by.zemich.advertisementservice.domain.entity.User;
import by.zemich.advertisementservice.domain.valueobject.*;

import java.time.LocalDateTime;
import java.util.UUID;

public class AdvertisementFactory {
    public static Advertisement create(Id userId,
                                       Category category,
                                       Condition condition,
                                       LocalDateTime publishedAt,
                                       LocalDateTime activatedAt,
                                       Price price,
                                       Comment comment,
                                       Photo photo) {
        return new Advertisement(
                new Id(UUID.randomUUID()),
                userId,
                category,
                condition,
                publishedAt,
                activatedAt,
                price,
                comment,
                photo,
                true
        );
    }
}
