package by.zemich.parser.domain.service.conditionanalizers.classifiers.api;

import by.zemich.parser.domain.model.Advertisement;

public interface ProductConditionClassifier {
    boolean analyze(Advertisement advertisement);

    boolean isApplicable(Advertisement advertisement);
}
