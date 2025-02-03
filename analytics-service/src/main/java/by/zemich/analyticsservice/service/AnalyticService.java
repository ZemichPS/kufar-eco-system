package by.zemich.analyticsservice.service;

import by.zemich.analyticsservice.domain.PriceAnalyzer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AnalyticService {
    private final PriceAnalyzer priceAnalyzer;

    public BigDecimal computeMarketPriceByPriceList(List<BigDecimal> prices) {
        try {
            return priceAnalyzer.getMarketPrice(prices);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public BigDecimal calculatePercentageDifference(BigDecimal val1, BigDecimal value2){
        return priceAnalyzer.calculatePercentageDifference(val1, value2);
    }
}
