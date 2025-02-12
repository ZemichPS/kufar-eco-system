package by.zemich.parser.application.service;

import by.zemich.parser.domain.model.Advertisement;
import by.zemich.parser.infrastructure.clients.dto.AdDetailsDTO;
import by.zemich.parser.domain.model.PriceStatistics;
import by.zemich.parser.domain.service.conditionanalizers.ConditionAnalyzer;
import by.zemich.parser.infrastructure.clients.KufarClient;
import by.zemich.parser.infrastructure.utils.Mapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Predicate;

@Service
@Slf4j
@RequiredArgsConstructor
@CacheConfig(
        cacheManager = "smartphoneAdvertisementServiceCacheManager",
        cacheNames = "marketPrices"
)
public class SmartphoneAdvertisementService {
    private final AdvertisementService advertisementService;
    private final MarketPriceService marketPriceService;

    private final Predicate<Advertisement> fullFunctionalPredicate = Advertisement::isFullyFunctional;
    private final Predicate<Advertisement> commerceAdPredicate = Advertisement::isCompanyAd;
    private final Predicate<Advertisement> unCommerceAdPredicate = advertisement -> !advertisement.isCompanyAd();

    @Cacheable(
            key = "#advertisement.brand.get() + " +
                    "'-' + #advertisement.model.get() + " +
                    "'-' + #advertisement.getParameterValueByParameterName('phablet_phones_memory').get()",
            sync = true,
            condition = "#advertisement.brand.isPresent() and #advertisement.model.isPresent() and #advertisement.getParameterValueByParameterName('phablet_phones_memory').isPresent()"
    )
    public Optional<BigDecimal> getMarketCommercePriceByAdvertisement(Advertisement advertisement) {
        return getMarketCommercePriceByAdvertisementAndPredicate(advertisement, fullFunctionalPredicate.and(commerceAdPredicate));
    }

    @Cacheable(
            key = "#advertisement.brand.get() + " +
                    "'-' + #advertisement.model.get() + " +
                    "'-' + #advertisement.getParameterValueByParameterName('phablet_phones_memory').get()",
            sync = true,
            condition = "#advertisement.brand.isPresent() and #advertisement.model.isPresent() and #advertisement.getParameterValueByParameterName('phablet_phones_memory').isPresent()"
    )
    public Optional<BigDecimal> getMarketUnCommercePriceByAdvertisement(Advertisement advertisement) {
        return getMarketCommercePriceByAdvertisementAndPredicate(advertisement, fullFunctionalPredicate.and(unCommerceAdPredicate));
    }

    private Optional<BigDecimal> getMarketCommercePriceByAdvertisementAndPredicate(Advertisement advertisement, Predicate<Advertisement> predicate) {
        if (!validateAdvertisement(advertisement)) return Optional.empty();
        String brand = advertisement.getBrand().orElseThrow();
        String model = advertisement.getModel().orElseThrow();
        String memoryAmount = advertisement.getParameterValueByParameterName("phablet_phones_memory").orElseThrow();
        List<Advertisement> advertisements = advertisementService.getAllByBrandAndModelWithMemoryAmount(brand, model, memoryAmount);
        BigDecimal marketPriceForCommerce;
        try {
            marketPriceForCommerce = marketPriceService.getMarketPrice(
                    advertisements,
                    predicate,
                    advertisement.getCondition()
            );
        } catch (Exception e) {
            log.error(e.getMessage());
            return Optional.empty();
        }
        return Optional.of(marketPriceForCommerce);
    }

    private boolean validateAdvertisement(Advertisement ad) {
        return ad.getBrand().isPresent()
                && ad.getModel().isPresent()
                && ad.getParameterValueByParameterName("phablet_phones_memory").isPresent();
    }


}
