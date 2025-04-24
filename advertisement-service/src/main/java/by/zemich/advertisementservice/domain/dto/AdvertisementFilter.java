package by.zemich.advertisementservice.domain.dto;


import by.zemich.advertisementservice.infrastracture.output.repository.jpa.specifications.AdvertisementSpecificationFilter;
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

    enum Condition {
        NEW,
        USED,
        BROKEN;
    }

    enum Side {
        BUY,
        SEll
    }
}
