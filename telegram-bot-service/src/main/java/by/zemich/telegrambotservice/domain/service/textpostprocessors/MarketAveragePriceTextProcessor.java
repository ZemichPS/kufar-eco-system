package by.zemich.telegrambotservice.domain.service.textpostprocessors;

import by.zemich.telegrambotservice.domain.model.KufarAdvertisement;
import by.zemich.telegrambotservice.domain.service.PriceAnalyzer;
import by.zemich.telegrambotservice.domain.service.textpostprocessors.api.PostTextProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.function.Predicate;

@Component
@RequiredArgsConstructor
@Log4j2
@Order(value = 6)
public class MarketAveragePriceTextProcessor implements PostTextProcessor {
    private final PriceAnalyzer priceAnalyzer;

    @Override
    public String process(KufarAdvertisement advertisement) {
        if (advertisement.getNonCommerceMarketPrice().get().signum() > 0 || advertisement.getCommerceMarketPrice().get().signum() > 0) {
            Predicate<BigDecimal> currentValueMoreThenZero = price -> price.compareTo(BigDecimal.ZERO) > 0;
            BigDecimal currentAdPrice = advertisement.getPriceInByn();

            StringBuilder rezult = new StringBuilder("\uD83D\uDCC8 Средняя рыночная стоимость c учётом состояния и объёма памяти:");

            advertisement.getCommerceMarketPrice()
                    .filter(currentValueMoreThenZero)
                    .map(commerceMarketPrice -> rezult.append("\n - %.0f (для коммерческих объявлений). ".formatted(commerceMarketPrice))
                            .append(getPercentageDifference(commerceMarketPrice, currentAdPrice)));

            advertisement.getNonCommerceMarketPrice()
                    .filter(currentValueMoreThenZero)
                    .map(notCommerceMarketPrice -> rezult.append("\n - %.0f (для частных объявлений). ".formatted(notCommerceMarketPrice))
                            .append(getPercentageDifference(notCommerceMarketPrice, currentAdPrice))
                    );

            return rezult.toString();
        } else return "";
    }

    @Override
    public boolean isApplicable(KufarAdvertisement advertisement) {
        return advertisement.getBrand().isPresent()
                && advertisement.getModel().isPresent()
                && advertisement.getPriceInByn().signum() > 0;
    }

    private String getPercentageDifference(BigDecimal comparePrice, BigDecimal currentPrice) {
        return "Разница %.0f%%;".formatted(priceAnalyzer.calculatePercentageDifference(comparePrice, currentPrice));
    }
}

