package by.zemich.parser.application.service;

import by.zemich.parser.domain.model.Advertisement;
import by.zemich.parser.domain.service.PriceAnalyzer;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

@Service
@AllArgsConstructor
public class MarketPriceService {

    private final PriceAnalyzer priceAnalyzer;

    private final int adsCountForMarketPriceAnalyze = 35;

    public BigDecimal getMarketPrice(List<Advertisement> advertisements,
                                     Predicate<Advertisement> predicate,
                                     String condition
    ) {
        List<BigDecimal> prices = advertisements.stream()
                .filter(predicate)
                .filter(ad -> ad.getCondition().equalsIgnoreCase(condition))
                .sorted(Comparator.comparing(Advertisement::getPublishedAt).reversed())
                .limit(adsCountForMarketPriceAnalyze)
                .map(Advertisement::getPriceInByn)
                .toList();
        return priceAnalyzer.getMarketPrice(prices);
    }
}
