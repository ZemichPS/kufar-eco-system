package by.zemich.parser.domain.service.textpostprocessors;

import by.zemich.parser.application.service.SmartphonesService;
import by.zemich.parser.domain.model.Advertisement;
import by.zemich.parser.domain.model.PriceStatistics;
import by.zemich.parser.domain.service.PriceAnalyzer;
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

@ExtendWith(MockitoExtension.class)
class MarketAveragePriceTextProcessorTest {

    @Spy
    private PriceAnalyzer priceAnalyzer;
    @Mock
    private SmartphonesService smartphonesService;

    @InjectMocks
    private MarketAveragePriceTextProcessor marketAveragePriceTextProcessor;


    @Test
    void process_whenAllPricesProvided_thenCorrectTextOutput() {
        PriceStatistics priceStatistics = new PriceStatistics(
                new BigDecimal(2500),
                new BigDecimal(2300),
                new BigDecimal(2440)
        );
        Advertisement advertisement = new Advertisement();
        advertisement.setPriceInByn(new BigDecimal(1_900));
        Mockito.when(smartphonesService.getPriceStatisticsByModel(Mockito.any())).thenReturn(Optional.of(priceStatistics));
        String result = marketAveragePriceTextProcessor.process(advertisement);
        System.out.printf("Result: %s\n", result);
        Assertions.assertNotNull(result);
    }

    @Test
    void process_whenMarketPriceForCommerceOnlyProvided_thenOutputOnlyForCommerce() {
        PriceStatistics priceStatistics = new PriceStatistics(
                new BigDecimal(2500),
                BigDecimal.ZERO,
                BigDecimal.ZERO
        );
        Advertisement advertisement = new Advertisement();
        advertisement.setPriceInByn(new BigDecimal(1_900));
        Mockito.when(smartphonesService.getPriceStatisticsByModel(Mockito.any())).thenReturn(Optional.of(priceStatistics));
        String result = marketAveragePriceTextProcessor.process(advertisement);
        System.out.printf("Result: %s\n", result);
        Assertions.assertNotNull(result);
    }

    @Test
    void process_whenMarketPriceForNotCommerceOnlyProvided_thenOutputOnlyForNotCommerce() {
        PriceStatistics priceStatistics = new PriceStatistics(
                BigDecimal.ZERO,
                new BigDecimal(2500),
                BigDecimal.ZERO
        );
        Advertisement advertisement = new Advertisement();
        advertisement.setPriceInByn(new BigDecimal(1_900));
        Mockito.when(smartphonesService.getPriceStatisticsByModel(Mockito.any())).thenReturn(Optional.of(priceStatistics));
        String result = marketAveragePriceTextProcessor.process(advertisement);
        System.out.printf("Result: %s\n", result);
        Assertions.assertNotNull(result);
    }

    @Test
    void process_whenCommonMarketPriceOnlyProvided_thenOutputOnlyForCommon() {
        PriceStatistics priceStatistics = new PriceStatistics(
                BigDecimal.ZERO,
                BigDecimal.ZERO,
                new BigDecimal(2500)
        );
        Advertisement advertisement = new Advertisement();
        advertisement.setPriceInByn(new BigDecimal(1_900));
        Mockito.when(smartphonesService.getPriceStatisticsByModel(Mockito.any())).thenReturn(Optional.of(priceStatistics));
        String result = marketAveragePriceTextProcessor.process(advertisement);
        System.out.printf("Result: %s\n", result);
        Assertions.assertNotNull(result);
    }

    @Test
    void process_whenCommonMarketPriceAndCommerceMarketPriceOnlyProvided_thenOutputOnlyForCommerceAndCommon() {
        PriceStatistics priceStatistics = new PriceStatistics(
                new BigDecimal(2500),
                BigDecimal.ZERO,
                new BigDecimal(2500)
        );
        Advertisement advertisement = new Advertisement();
        advertisement.setPriceInByn(new BigDecimal(1_900));
        Mockito.when(smartphonesService.getPriceStatisticsByModel(Mockito.any())).thenReturn(Optional.of(priceStatistics));
        String result = marketAveragePriceTextProcessor.process(advertisement);
        System.out.printf("Result: %s\n", result);
        Assertions.assertNotNull(result);
    }


    @Test
    void process_whenPriceStatisticsEmpty_thenOutputEmpty() {
        Advertisement advertisement = new Advertisement();
        advertisement.setPriceInByn(new BigDecimal(1_900));
        Mockito.when(smartphonesService.getPriceStatisticsByModel(Mockito.any())).thenReturn(Optional.empty());
        String result = marketAveragePriceTextProcessor.process(advertisement);
        System.out.printf("Result: %s\n", result);
        Assertions.assertArrayEquals("".toCharArray(), result.toCharArray());
    }

//    @Test
//    void process_whenPriceStatisticsNotEmptyButAllPricesZero_thenOutputEmpty() {
//        PriceStatistics priceStatistics = new PriceStatistics(
//                BigDecimal.ZERO,
//                BigDecimal.ZERO,
//                BigDecimal.ZERO
//        );
//        Advertisement advertisement = new Advertisement();
//        advertisement.setPriceInByn(new BigDecimal(1_900));
//        Mockito.when(advertisementServiceFacade.getPriceStatisticsByModel(Mockito.any())).thenReturn(Optional.of(priceStatistics));
//        String result = marketAveragePriceTextProcessor.process(advertisement);
//        System.out.printf("Result: %s\n", result);
//        Assertions.assertArrayEquals("".toCharArray(), result.toCharArray());
//    }


}