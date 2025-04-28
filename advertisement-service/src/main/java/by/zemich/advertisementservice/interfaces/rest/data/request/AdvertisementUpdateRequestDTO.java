package by.zemich.advertisementservice.interfaces.rest.data.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AdvertisementUpdateRequestDTO {
    private String condition;
    private BigDecimal price;
    private String comment;

    public enum Condition {
        NEW,
        USED,
        BROKEN;
    }
}
