package by.zemich.parser.domain.model.criterias;

import by.zemich.parser.domain.model.Advertisement;

import java.math.BigDecimal;

public class OnlyWithPriceCriteria implements Criteria {
    @Override
    public boolean isSatisfied(Advertisement advertisement) {
        return advertisement.getPriceInByn().compareTo(BigDecimal.ZERO) > 0;
    }
}
