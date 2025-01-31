package by.zemich.kufar.domain.service.textpostprocessors;

import by.zemich.kufar.domain.model.Advertisement;
import by.zemich.kufar.application.service.SmartphonesService;
import by.zemich.kufar.domain.service.PriceAnalyzer;
import by.zemich.kufar.domain.service.textpostprocessors.api.PostTextProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.function.Predicate;

@Component
@RequiredArgsConstructor
@Log4j2
@Order(value = 6)
public class MarketAveragePriceTextProcessor implements PostTextProcessor {

    private final SmartphonesService smartphonesService;
    private final PriceAnalyzer priceAnalyzer;

    @Override
    public String process(Advertisement advertisement) {

        Predicate<BigDecimal> currentValueMoreThenZero = price -> price.compareTo(BigDecimal.ZERO) > 0;
        BigDecimal currentAdPrice = advertisement.getPriceInByn();

        return smartphonesService.getPriceStatisticsByModel(advertisement)
                .map(statistic -> {
                    StringBuilder rezult = new StringBuilder("\uD83D\uDCC8 Средняя рыночная стоимость c учётом состояния и объёма памяти:");

                    Optional.of(statistic.marketPriceForCommerce())
                            .filter(currentValueMoreThenZero)
                            .map(commerceMarketPrice -> rezult.append("\n - %.0f (для коммерческих объявлений). ".formatted(commerceMarketPrice))
                                    .append(getPercentageDifference(commerceMarketPrice, currentAdPrice)));

                    Optional.of(statistic.marketPriceForNotCommerce())
                            .filter(currentValueMoreThenZero)
                            .map(notCommerceMarketPrice -> rezult.append("\n - %.0f (для частных объявлений). ".formatted(notCommerceMarketPrice))
                                    .append(getPercentageDifference(notCommerceMarketPrice, currentAdPrice))
                            );
                    Optional.of(statistic.commonMarketPrice())
                            .filter(currentValueMoreThenZero)
                            .map(commonMarketPrice -> rezult.append("\n - %.0f (общая). ".formatted(commonMarketPrice))
                                    .append(getPercentageDifference(commonMarketPrice, currentAdPrice)));

                    return rezult.toString();
                }).orElse("");

    }

    @Override
    public boolean isApplicable(Advertisement advertisement) {
        return advertisement.getBrand().isPresent()
                && advertisement.getModel().isPresent()
                && advertisement.getPriceInByn().compareTo(BigDecimal.ZERO) > 0;
    }

    private String getPercentageDifference(BigDecimal comparePrice, BigDecimal currentPrice) {
        return "Разница %.0f%%;".formatted(priceAnalyzer.calculatePercentageDifference(comparePrice, currentPrice));
    }

}
