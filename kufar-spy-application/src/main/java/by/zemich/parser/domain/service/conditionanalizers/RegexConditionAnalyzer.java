package by.zemich.parser.domain.service.conditionanalizers;

import by.zemich.parser.domain.model.Advertisement;
import by.zemich.parser.domain.service.conditionanalizers.classifiers.api.ProductConditionClassifier;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RegexConditionAnalyzer implements ConditionAnalyzer {

    private final List<ProductConditionClassifier> classifiers;


    @Override
    public boolean isFullyFunctional(Advertisement advertisement) {
        return classifiers.stream()
                .noneMatch(classifier -> classifier.analyze(advertisement));
    }
}



