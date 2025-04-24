package by.zemich.advertisementservice.domain.command;

import by.zemich.advertisementservice.domain.valueobject.*;

public record UpdateAdvertisementCommand(
        AdvertisementId advertisementId,
        Condition condition,
        Price price,
        Comment comment
        ) {
}
