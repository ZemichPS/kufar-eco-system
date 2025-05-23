package by.zemich.parser.domain.model.criterias;

import by.zemich.parser.domain.model.Advertisement;

import java.math.BigDecimal;

public class PriceCriteria implements Criteria{

    private BigDecimal min;
    private BigDecimal max;

    public PriceCriteria(BigDecimal min, BigDecimal max) {
        this.min = min;
        this.max = max;
    }

    @Override
    public boolean isSatisfied(Advertisement advertisement) {
        BigDecimal cost = advertisement.getPriceInByn();
        return cost.compareTo(min) > 0 && cost.compareTo(max) < 0;
    }
}
