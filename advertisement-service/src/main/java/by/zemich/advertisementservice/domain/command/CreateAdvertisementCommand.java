package by.zemich.advertisementservice.domain.command;

import by.zemich.advertisementservice.domain.valueobject.*;

import java.time.LocalDateTime;

public record CreateAdvertisementCommand(
        AdvertisementId advertisementId,
        UserId userId,
        CategoryId categoryId,
        Condition condition,
        Price price,
        LocalDateTime publishedAt,
        Comment comment,
        boolean active,
        Photo photo,
        Side side
        ) {
}
