package by.zemich.parser.domain.service.conditionanalizers;

import by.zemich.parser.domain.model.Advertisement;

public interface ConditionAnalyzer {
    boolean isFullyFunctional(Advertisement advertisement);
}
