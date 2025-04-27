package by.zemich.advertisementservice.domain.dto;


import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class AdvertisementFilter {
    private String categoryName;
    private Condition condition;
    private LocalDateTime publishedAt;
    private BigDecimal priceFrom;
    private BigDecimal priceTo;
    private Boolean active;
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
