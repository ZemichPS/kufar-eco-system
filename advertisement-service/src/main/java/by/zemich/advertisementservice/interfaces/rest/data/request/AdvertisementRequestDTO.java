package by.zemich.advertisementservice.interfaces.rest.data.request;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class AdvertisementRequestDTO {
    private String condition;
    private BigDecimal price;
    private String comment;

    public enum Condition {
        NEW,
        USED,
        BROKEN;
    }
}
