package by.zemich.advertisementservice.interfaces.rest.data.request;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class AdvertisementRequestDTO {
    private UUID advertisementId;
    Condition condition;
    BigDecimal price;
    String comment;

    public enum Condition {
        NEW,
        USED,
        BROKEN;
    }
}
