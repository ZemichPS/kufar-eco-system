package by.zemich.advertisementservice.domain.command;

import by.zemich.advertisementservice.domain.valueobject.*;
import lombok.Builder;
import lombok.Data;

import java.util.Map;
import java.util.UUID;

@Data
@Builder
public class AdvertisementCreateCommand {
    Id userId;
    Id categoryId;
    Condition condition;
    Price price;
    Comment comment;
    Photo photo;
    Map<UUID, String> attributesMap;
}
