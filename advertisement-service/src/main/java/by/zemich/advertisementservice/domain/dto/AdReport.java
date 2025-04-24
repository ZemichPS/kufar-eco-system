package by.zemich.advertisementservice.domain.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AdReport {
    private Integer adCount;
    private Integer activeCount;
    private Integer unActiveCount;
    private BigDecimal sum;
}
