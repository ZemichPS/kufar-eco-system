package by.zemich.analyticsservice.presentation;

import by.zemich.analyticsservice.presentation.dto.request.MarketPriceRequest;
import by.zemich.analyticsservice.presentation.dto.request.PercentageDifferenceRequest;
import by.zemich.analyticsservice.service.AnalyticService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/pricing")
public class AnalyticController {
    private final AnalyticService analyticService;

    @PostMapping("/market")
    public ResponseEntity<BigDecimal> getMarketPrice(@RequestBody MarketPriceRequest marketPriceRequest){
        List<BigDecimal> prices = marketPriceRequest.getPrices();
        try{
            BigDecimal marketPrice = analyticService.computeMarketPriceByPriceList(prices);
            return ResponseEntity.ok(marketPrice);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/percentage")
    public ResponseEntity<BigDecimal> getPercentageDifference(@RequestBody PercentageDifferenceRequest differenceRequest){
        BigDecimal val1 = differenceRequest.getVal1();
        BigDecimal val2 = differenceRequest.getVal2();
        BigDecimal result = analyticService.calculatePercentageDifference(val1, val2);
        return ResponseEntity.ok(result);
    }


}
