package by.zemich.advertisementservice.interfaces.rest.data.request;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
public class AdvertisementRequestDTO {
    private UUID id;
    private UUID userId;
    private UUID categoryId;
    private NewAdvertisementDTO.Condition condition;
    private BigDecimal priceInByn;
    private String comment;
    private String photo;
    private List<NewAdvertisementDTO.AdvertisementAttribute> attributes;

    public enum Condition {
        NEW,
        USED,
        BROKEN;
    }

    @Data
    public static class AdvertisementAttribute {
        private UUID categoryAttributeId;
        private String value;
    }
}
