package by.zemich.telegrambotservice.infrastructure.clients;

import by.zemich.telegrambotservice.infrastructure.clients.dto.MarketPriceRequest;
import by.zemich.telegrambotservice.infrastructure.clients.dto.PercentageDifferenceRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.math.BigDecimal;

@FeignClient("analytic-service")
public interface AnalyticServiceFeignClient {
    @RequestMapping(method = RequestMethod.POST, value = "api/v1/pricing/market")
    BigDecimal getMarketPrice(MarketPriceRequest request);

    @RequestMapping(method = RequestMethod.POST, value = "api/v1/pricing/percentage")
    BigDecimal getPercentageDifference(PercentageDifferenceRequest differenceRequest);

}
