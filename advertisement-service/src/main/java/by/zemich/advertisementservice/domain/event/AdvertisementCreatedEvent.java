package by.zemich.advertisementservice.domain.event;

import java.util.UUID;

public record AdvertisementCreatedEvent(UUID advertisementId) {
}
