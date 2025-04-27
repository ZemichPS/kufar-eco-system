package by.zemich.advertisementservice.domain.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Value;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * DTO for {@link by.zemich.advertisementservice.infrastracture.output.repository.jpa.entity.AdvertisementEntity}
 */
@Value
@Builder
@Data
public class FullAdvertisementDto implements Serializable {
    UUID uuid;
    UUID userUuid;
    String category;
    String condition;
    LocalDateTime publishedAt;
    BigDecimal priceInByn;
    String comment;
    String photoFileName;
    Boolean active;
    String side;
    List<AdvertisementAttributeDto> attributes;

    /**
     * DTO for {@link by.zemich.advertisementservice.infrastracture.output.repository.jpa.entity.AdvertisementAttributeEntity}
     */
    @Value
    @Builder
    public static class AdvertisementAttributeDto implements Serializable {
        String name;
        String value;
    }
}