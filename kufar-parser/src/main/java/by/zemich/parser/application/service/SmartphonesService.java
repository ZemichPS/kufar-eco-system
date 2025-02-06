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
        cacheManager = "caffeineCacheManager",
        cacheNames = "priceStatistics"
)
public class SmartphonesService {
    private final AdvertisementService advertisementService;
    private final ConditionAnalyzer conditionAnalyzer;
    private final KufarClient kufarClient;
    private final ModelService modelService;
    private final ManufactureService manufactureService;
    private final MarketPriceService marketPriceService;


    private final Predicate<Advertisement> fullFunctionalPredicate = Advertisement::isFullyFunctional;
    private final Predicate<Advertisement> commerceAdPredicate = Advertisement::isCompanyAd;
    private final Predicate<Advertisement> notCommerceAdPredicate = advertisement -> !advertisement.isCompanyAd();

    @Cacheable(
            key = "#advertisement.brand.get() + '-' + #advertisement.model.get() + '-' + #advertisement.getParameterValueByParameterName('phablet_phones_memory').get()",
            sync = true,
            condition = "#advertisement.brand.isPresent() and #advertisement.model.isPresent() and #advertisement.getParameterValueByParameterName('phablet_phones_memory').isPresent()"
    )
    public Optional<PriceStatistics> getPriceStatisticsByModel(Advertisement advertisement) {

        if (advertisement.getBrand().isEmpty() || advertisement.getModel().isEmpty()) return Optional.empty();

        String brand = advertisement.getBrand().orElse("");
        String model = advertisement.getModel().orElse("");

        String memoryAmount = advertisement.getParameterValueByParameterName("phablet_phones_memory").orElse("");
        if (memoryAmount.isEmpty()) return Optional.empty();

        List<Advertisement> advertisements = advertisementService.getAllByBrandAndModelWithMemoryAmount(brand, model, memoryAmount);

        BigDecimal marketPriceForCommerce;
        try {
            marketPriceForCommerce = marketPriceService.getMarketPrice(
                    advertisements,
                    fullFunctionalPredicate.and(commerceAdPredicate),
                    advertisement.getCondition()
            );
        } catch (Exception e) {
            marketPriceForCommerce = BigDecimal.ZERO;
        }

        BigDecimal marketPriceForNotCommerce;
        try {
            marketPriceForNotCommerce = marketPriceService.getMarketPrice(
                    advertisements,
                    fullFunctionalPredicate.and(notCommerceAdPredicate),
                    advertisement.getCondition()
            );
        } catch (Exception e) {
            marketPriceForNotCommerce = BigDecimal.ZERO;
        }

        BigDecimal commonMarketPrice;
        try {
            commonMarketPrice = marketPriceService.getMarketPrice(
                    advertisements,
                    fullFunctionalPredicate,
                    advertisement.getCondition());
        } catch (Exception e) {
            commonMarketPrice = BigDecimal.ZERO;
        }

        if (marketPriceForCommerce.compareTo(BigDecimal.ZERO) == 0
                & marketPriceForNotCommerce.compareTo(BigDecimal.ZERO) == 0
                & commonMarketPrice.compareTo(BigDecimal.ZERO) == 0
        ) return Optional.empty();

        return Optional.of(new PriceStatistics(
                marketPriceForCommerce,
                marketPriceForNotCommerce,
                commonMarketPrice)
        );
    }

    public void updateAdvertisementCauseNewConditionRules() {
        advertisementService.getAll().stream().parallel()
                .filter(advertisement -> advertisement.getCategory().equalsIgnoreCase("17010"))
                .peek(advertisement -> {
                    boolean result = conditionAnalyzer.isFullyFunctional(advertisement);
                    advertisement.setFullyFunctional(result);
                }).toList().stream().parallel()
                .forEach(advertisementService::save);
    }

    public void parseSmartphonesAdsToDB() {
        modelService.getAll().stream()
                .map(model -> {
                    Long manufactureId = model.getManufacturer().getId();
                    String modelKufarId = model.getKufarId();
                    Map<String, String> paramMap = new HashMap<>();
                    paramMap.put("cat", "17010");
                    paramMap.put("lang", "ru");
                    paramMap.put("cmp", "0");
                    paramMap.put("pb", manufactureId.toString());
                    paramMap.put("phm", modelKufarId);
                    paramMap.put("prn", "17000");
                    paramMap.put("size", "100");
                    paramMap.put("sort", "lst.d");
                    return paramMap;
                })
                .map(kufarClient::getAdsByParameters)
                .filter(Objects::nonNull)
                .flatMap(adsDTO -> adsDTO.getAds().stream().parallel())
                .filter(Objects::nonNull)
                .filter(adsDTO -> !advertisementService.existsByLink(adsDTO.getAdId()))
                .map(adDTO -> {
                    Advertisement advertisement = Mapper.mapToEntity(adDTO);
                    adDTO.getAdParameters().stream()
                            .map(Mapper::mapToEntity)
                            .forEach(advertisement::addParameter);
                    return advertisement;
                })
                .peek(advertisement -> {
                    AdDetailsDTO detailsDTO = kufarClient.getDetails(advertisement.getAdId());
                    String details = detailsDTO.getResult().getBody();
                    advertisement.setDetails(details);
                    advertisement.setFullyFunctional(conditionAnalyzer.isFullyFunctional(advertisement));
                })
                .forEach(advertisementService::save);
    }

    private Map<String, String> getParamMap(String manufactureId, String modelId) {
        return Map.of(
                "cat", "17010",
                "cmp", "0",
                "cnd", "1",
                "lang", "ru",
                "pb", manufactureId,
                "phm", modelId,
                "prn", "17000",
                "size", "600",
                "sort", "lst.d"
        );
    }
}
