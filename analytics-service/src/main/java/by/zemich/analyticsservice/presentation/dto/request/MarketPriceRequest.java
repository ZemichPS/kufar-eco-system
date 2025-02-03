package by.zemich.analyticsservice.presentation.dto.request;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class MarketPriceRequest {
    private List<BigDecimal> prices;

}
