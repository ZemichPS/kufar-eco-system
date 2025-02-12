package by.zemich.telegrambotservice.infrastructure.messeging.mapper;

import by.zemich.telegrambotservice.domain.model.KufarAdvertisement;
import org.apache.avro.generic.GenericRecord;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component


public class AdvertisementMapper {

    public static KufarAdvertisement map(GenericRecord record) {

        List<KufarAdvertisement.Parameter> parameters = mapParameters(record);

        return KufarAdvertisement.builder()
                .id(UUID.fromString(record.get("id").toString()))
                .kufarId(Long.parseLong(record.get("kufarId").toString()))
                .link(record.get("link").toString())
                .parentCategory(record.get("parentCategory").toString())
                .category(record.get("category").toString())
                .companyAd(Boolean.getBoolean(record.get("commerce").toString()))
                .subject(record.get("subject").toString())
                .publishedAt(convertTimestamp(record.get("publishedAt")))
                .type(record.get("type").toString())
                .priceInByn(convertDecimal(record, "priceInByn"))
                .priceInUsd(convertDecimal(record, "priceInUsd"))
                .commerceMarketPrice(convertDecimal(record, "commerceMarketPrice"))
                .nonCommerceMarketPrice(convertDecimal(record, "nonCommerceMarketPrice"))
                .details(record.get("details").toString())
                .fullyFunctional(Boolean.getBoolean(record.get("fullyFunctional").toString()))
                .images(record.get("images").toString())
                .sellerId(record.get("sellerId").toString())
                .parameters(parameters)
                .build();
    }

    private static List<KufarAdvertisement.Parameter> mapParameters(GenericRecord record) {
        List<GenericRecord> parameterRecords = (List<GenericRecord>) record.get("parameters"); // Получаем List<GenericRecord>
        return parameterRecords.stream()
                .map(AdvertisementMapper::mapParameter)
                .collect(Collectors.toList());
    }

    private static KufarAdvertisement.Parameter mapParameter(GenericRecord parameterRecord) {
        return KufarAdvertisement.Parameter.builder().
                identity(parameterRecord.get("identity").toString())
                .value(parameterRecord.get("value").toString())
                .label(parameterRecord.get("label").toString()).build();
    }

    public static BigDecimal convertDecimal(GenericRecord record, String fieldName) {
        ByteBuffer byteBuffer = (ByteBuffer) record.get(fieldName);
        if (byteBuffer != null) {
            return new BigDecimal(new java.math.BigInteger(byteBuffer.array()), 0); // Указываем scale (масштаб)
        }
        return BigDecimal.ZERO;
    }

    private static LocalDateTime convertTimestamp(Object value) {
        if (value instanceof Long timestampMicros) {
            return Instant.ofEpochSecond(timestampMicros / 1_000_000, (timestampMicros % 1_000_000) * 1_000)
                    .atZone(ZoneId.of("UTC"))
                    .toLocalDateTime();
        }
        return null;
    }
}
