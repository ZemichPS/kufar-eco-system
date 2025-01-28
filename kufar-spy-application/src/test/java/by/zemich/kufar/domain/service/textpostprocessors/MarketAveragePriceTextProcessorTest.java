package by.zemich.kufar.domain.service.textpostprocessors;

import by.zemich.kufar.application.service.AdvertisementServiceFacade;
import by.zemich.kufar.domain.model.Advertisement;
import by.zemich.kufar.domain.model.PriceStatistics;
import by.zemich.kufar.domain.service.PriceAnalyzer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class MarketAveragePriceTextProcessorTest {

    @Spy
    private PriceAnalyzer priceAnalyzer;
    @Mock
    private AdvertisementServiceFacade advertisementServiceFacade;

    @InjectMocks
    private MarketAveragePriceTextProcessor marketAveragePriceTextProcessor;


    @Test
    void process() {
        PriceStatistics priceStatistics = new PriceStatistics(
                new BigDecimal(2500),
                new BigDecimal(2300),
                new BigDecimal(2440)
        );
        Advertisement advertisement = new Advertisement();
        advertisement.setPriceInByn(new BigDecimal(1_900));
        Mockito.when(advertisementServiceFacade.getPriceStatisticsByModel(Mockito.any())).thenReturn(Optional.of(priceStatistics));
        String result = marketAveragePriceTextProcessor.process(advertisement);
        System.out.printf("Result: %s\n", result);
        Assertions.assertNotNull(result);

    }
}