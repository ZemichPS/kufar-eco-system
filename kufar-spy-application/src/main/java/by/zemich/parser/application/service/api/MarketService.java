package by.zemich.parser.application.service.api;

import by.zemich.parser.domain.model.Advertisement;

import java.math.BigDecimal;

public interface MarketService {
    ProductDataDto getProductDataByAdvertisement(Advertisement advertisement);
    String getMarketName();
    BigDecimal getProductPrice(Advertisement advertisement);

    record ProductDataDto(String linkOnProductPage, BigDecimal price){};
}
