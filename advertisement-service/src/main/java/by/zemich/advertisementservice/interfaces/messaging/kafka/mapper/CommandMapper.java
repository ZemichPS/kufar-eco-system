package by.zemich.advertisementservice.interfaces.messaging.kafka.mapper;

import by.zemich.advertisementservice.domain.valueobject.*;
import lombok.experimental.UtilityClass;
import org.apache.avro.generic.GenericRecord;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@UtilityClass
public class CommandMapper {
    public static AdvertisementCreateCommand mapToCommand(GenericRecord record) {
        Map<String, String> avroMap = (Map<String, String>) record.get("attributesMap");
        Id userId = new Id(UUID.fromString(record.get("userUuid").toString()));
        Id categoryId = new Id(UUID.fromString(record.get("categoryUuid").toString()));;
        Condition condition = Condition.valueOf(record.get("condition").toString());
        Price price = new Price(new BigDecimal(record.get("priceInByn").toString()));
        Comment comment = new Comment(record.get("comment").toString());
        Photo photo = new Photo(record.get("photoFilename").toString());
        Map<UUID, String> attributesMap =avroMap.entrySet()
                .stream()
                .collect(Collectors.toMap(e -> UUID.fromString(e.getKey()), Map.Entry::getValue));
        return AdvertisementCreateCommand.builder()
                .userId(userId)
                .categoryId(categoryId)
                .condition(condition)
                .price(price)
                .comment(comment)
                .photo(photo)
                .attributesMap(attributesMap)
                .build();
    }
}
