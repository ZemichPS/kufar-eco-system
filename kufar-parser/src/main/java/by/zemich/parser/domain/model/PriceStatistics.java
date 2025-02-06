package by.zemich.parser.domain.model;

import java.math.BigDecimal;

public record PriceStatistics(BigDecimal marketPriceForCommerce,
                              BigDecimal marketPriceForNotCommerce,
                              BigDecimal commonMarketPrice
) {

}
