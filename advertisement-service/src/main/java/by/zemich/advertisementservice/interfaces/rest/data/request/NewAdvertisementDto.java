package by.zemich.advertisementservice.interfaces.rest.data.request;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class NewAdvertisementDto {
    private UUID categoryId;
    private Condition condition;
    private BigDecimal priceInByn;
    private String comment;
    private Side side;

    public enum Condition {
        NEW,
        USED,
        BROKEN;
    }

    public enum Side {
        BUY,
        SELL
    }


}

