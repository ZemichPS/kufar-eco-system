package by.zemich.advertisementservice.domain.dto;


import by.zemich.advertisementservice.domain.valueobject.UserId;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class AdvertisementFilter {
    private String categoryName;
    private Condition condition;
    private LocalDateTime publishedAt;
    private BigDecimal priceFrom;
    private BigDecimal priceTo;
    private Boolean active;
    private Side side;
    private UserId userId;

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
