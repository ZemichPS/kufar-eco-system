package by.zemich.telegrambotservice.infrastructure.clients.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
public class MarketPriceRequest {
    private List<BigDecimal> prices;

}
