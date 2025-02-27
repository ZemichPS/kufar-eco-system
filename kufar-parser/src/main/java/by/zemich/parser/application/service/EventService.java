package by.zemich.parser.application.service;

import by.zemich.parser.domain.model.Advertisement;
import by.zemich.parser.domain.model.Category;
import by.zemich.parser.domain.model.events.AdvertisementCreatedEvent;
import by.zemich.parser.infrastructure.messaging.kafka.mapper.AdvertisementMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service
@AllArgsConstructor
public class EventService {
    private final AdvertisementMapper advertisementMapper;
    private final SmartphoneAdvertisementService smartphoneAdvertisementService;
    private final SubCategoryService subCategoryService;

    public AdvertisementCreatedEvent create(Advertisement advertisement) {
        AdvertisementCreatedEvent event = advertisementMapper.mapToEvent(advertisement);
        String categoryId = advertisement.getCategory();
        setCategory(event, categoryId);
        setMarketPrices(advertisement, event);
        return event;
    }

    private void setCategory(AdvertisementCreatedEvent event, String categoryId) {
        subCategoryService.getById(categoryId).ifPresentOrElse(
                subcategory -> {
                    Category category = subcategory.getCategory();
                    event.setCategory(subcategory.getName());
                    event.setParentCategory(category.getName());
                }, () -> {
                    event.setCategory("Unknown");
                    event.setParentCategory("Unknown");
                });
    }

    private void setMarketPrices(Advertisement advertisement, AdvertisementCreatedEvent event) {
        BigDecimal commerceMarketPrice;
        BigDecimal unCommerceMarketPrice;
        try {
            commerceMarketPrice = smartphoneAdvertisementService.getMarketCommercePriceByAdvertisement(advertisement).orElse(BigDecimal.ZERO);

        } catch (IllegalArgumentException e) {
            commerceMarketPrice = BigDecimal.ZERO;
            log.error("Failed in getting market price for commerce. Will be set ZERO value. Model: {} Cause: {}", advertisement.getModel().get(), e.getMessage());
        }
        try {
            unCommerceMarketPrice = smartphoneAdvertisementService.getMarketUnCommercePriceByAdvertisement(advertisement).orElse(BigDecimal.ZERO);
        } catch (IllegalArgumentException e) {
            unCommerceMarketPrice = BigDecimal.ZERO;
            log.error("Failed in getting market price for non commerce. Will be set ZERO value. Model: {} Cause: {}", advertisement.getModel().get(), e.getMessage());

        }
        event.setCommerceMarketPrice(commerceMarketPrice);
        event.setNonCommerceMarketPrice(unCommerceMarketPrice);
    }

}
