package by.zemich.advertisementservice.domain.entity.factory;

import by.zemich.advertisementservice.domain.entity.Advertisement;
import by.zemich.advertisementservice.domain.entity.Category;
import by.zemich.advertisementservice.domain.valueobject.UserId;
import by.zemich.advertisementservice.domain.valueobject.*;

import java.time.LocalDateTime;

public class AdvertisementFactory {
    public static Advertisement get(
            Id id,
            UserId userId,
            Category category,
            Condition condition,
            LocalDateTime publishedAt,
            LocalDateTime activatedAt,
            Price price,
            Comment comment,
            Photo photo) {

        return new Advertisement(
                id,
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
