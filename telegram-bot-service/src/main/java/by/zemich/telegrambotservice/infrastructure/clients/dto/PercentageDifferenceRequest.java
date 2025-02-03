package by.zemich.telegrambotservice.infrastructure.clients.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class PercentageDifferenceRequest {
    BigDecimal val1;
    BigDecimal val2;
}
