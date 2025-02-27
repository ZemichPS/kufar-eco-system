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
public class SmartphoneAdvertisementService {
    private final AdvertisementService advertisementService;
    private final MarketPriceService marketPriceService;

    private final Predicate<Advertisement> fullFunctionalPredicate = Advertisement::isFullyFunctional;
    private final Predicate<Advertisement> commerceAdPredicate = Advertisement::isCompanyAd;
    private final Predicate<Advertisement> unCommerceAdPredicate = advertisement -> !advertisement.isCompanyAd();

    public Optional<BigDecimal> getMarketCommercePriceByAdvertisement(Advertisement advertisement) {
        return getMarketCommercePriceByAdvertisementAndPredicate(advertisement, fullFunctionalPredicate.and(commerceAdPredicate));
    }

    public Optional<BigDecimal> getMarketUnCommercePriceByAdvertisement(Advertisement advertisement) {
        return getMarketCommercePriceByAdvertisementAndPredicate(advertisement, fullFunctionalPredicate.and(unCommerceAdPredicate));
    }

    private Optional<BigDecimal> getMarketCommercePriceByAdvertisementAndPredicate(Advertisement advertisement, Predicate<Advertisement> predicate) {
        if (!validateAdvertisement(advertisement)) {
            return Optional.empty();
        }
        String brand = advertisement.getBrand().orElseThrow(() -> new IllegalArgumentException("Brand is missing"));
        String model = advertisement.getModel().orElseThrow(() -> new IllegalArgumentException("Model is missing"));
        Optional<String> memoryOpt = advertisement.getParameterValueByParameterName("phablet_phones_memory");

        List<Advertisement> ads = memoryOpt
                .map(memory -> advertisementService.getAllByBrandAndModelWithMemoryAmount(brand, model, memory))
                .orElseGet(() -> advertisementService.getAllByBrandAndModel(brand, model));
        BigDecimal price = marketPriceService.getMarketPrice(ads, predicate, advertisement.getCondition());
        return Optional.of(price);
    }

    private boolean validateAdvertisement(Advertisement ad) {
        return ad.getBrand().isPresent()
                && ad.getModel().isPresent();
    }


}
