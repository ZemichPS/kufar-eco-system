package by.zemich.parser.domain.model.criterias;

import by.zemich.parser.domain.model.Advertisement;

public interface Criteria {
    boolean isSatisfied(Advertisement advertisement);
}
