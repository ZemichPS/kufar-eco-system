package by.zemich.analyticsservice.presentation.dto.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PercentageDifferenceRequest {
    BigDecimal val1;
    BigDecimal val2;
}
