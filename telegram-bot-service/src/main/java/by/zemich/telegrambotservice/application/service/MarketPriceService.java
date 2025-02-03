package by.zemich.telegrambotservice.application.service;

import by.zemich.telegrambotservice.domain.model.Advertisement;
import by.zemich.telegrambotservice.infrastructure.clients.AnalyticServiceFeignClient;
import by.zemich.telegrambotservice.infrastructure.clients.dto.MarketPriceRequest;
import by.zemich.telegrambotservice.infrastructure.clients.dto.PercentageDifferenceRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

@Service
@AllArgsConstructor
public class MarketPriceService {
    private final AnalyticServiceFeignClient analyticServiceClient;
    private final int adsCountForMarketPriceAnalyze = 35;


    public BigDecimal getMarketPrice(List<Advertisement> advertisements,
                                      Predicate<Advertisement> predicate,
                                      String condition
    ) throws Exception {
        List<BigDecimal> prices = advertisements.stream()
                .filter(predicate)
                .filter(ad -> ad.getCondition().equalsIgnoreCase(condition))
                .sorted(Comparator.comparing(Advertisement::getPublishedAt).reversed())
                .limit(adsCountForMarketPriceAnalyze)
                .map(Advertisement::getPriceInByn)
                .toList();
        return analyticServiceClient.getMarketPrice(new MarketPriceRequest(prices));
    }

    public BigDecimal getMarketPrice(List<BigDecimal> prices) throws Exception {
        return analyticServiceClient.getMarketPrice(new MarketPriceRequest(prices));
    }

    public BigDecimal calculatePercentageDifference(BigDecimal marketPriceForCommerce, BigDecimal currentAdPrice){
        return analyticServiceClient.getPercentageDifference(new PercentageDifferenceRequest(marketPriceForCommerce, currentAdPrice));
    }
}
