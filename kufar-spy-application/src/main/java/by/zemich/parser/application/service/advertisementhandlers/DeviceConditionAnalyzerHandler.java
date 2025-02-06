package by.zemich.parser.application.service.advertisementhandlers;

import by.zemich.parser.application.service.advertisementhandlers.api.AdvertisementProcessor;
import by.zemich.parser.domain.model.Advertisement;
import by.zemich.parser.domain.service.conditionanalizers.ConditionAnalyzer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeviceConditionAnalyzerHandler implements AdvertisementProcessor {
    private final ConditionAnalyzer conditionAnalyzer;

    @Override
    public void process(Advertisement advertisement) {
        boolean result = conditionAnalyzer.isFullyFunctional(advertisement);
        advertisement.setFullyFunctional(result);
    }

    @Override
    public boolean canProcess(Advertisement advertisement) {
        return true;
    }
}
