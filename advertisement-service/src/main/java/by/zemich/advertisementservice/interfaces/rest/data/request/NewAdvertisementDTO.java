package by.zemich.advertisementservice.interfaces.rest.data.request;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
public class NewAdvertisementDTO {
    private UUID userId;
    private UUID categoryId;
    private Condition condition;
    private BigDecimal priceInByn;
    private String comment;
    private String photo;
    private List<AdvertisementAttribute> attributes;

    public enum Condition {
        NEW,
        USED,
        BROKEN;
    }

    @Data
    public class AdvertisementAttribute {
        private UUID CategoryAttributeId;
        private String value;
    }
}

