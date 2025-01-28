package by.zemich.kufar.application.service.advertisementhandlers;

import by.zemich.kufar.application.service.advertisementhandlers.api.AdvertisementProcessor;
import by.zemich.kufar.domain.model.Advertisement;
import by.zemich.kufar.domain.service.conditionanalizers.ConditionAnalyzer;
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
