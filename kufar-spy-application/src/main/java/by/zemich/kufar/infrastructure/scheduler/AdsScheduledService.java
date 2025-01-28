package by.zemich.kufar.infrastructure.scheduler;

import by.zemich.kufar.application.service.*;
import by.zemich.kufar.domain.model.Advertisement;
import by.zemich.kufar.domain.model.Seller;
import by.zemich.kufar.infrastructure.clients.KufarClient;
import by.zemich.kufar.infrastructure.clients.NIOKufarClient;
import by.zemich.kufar.infrastructure.properties.CategoryParseListProperties;
import by.zemich.kufar.infrastructure.utils.Mapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.util.Objects;

@Service
@EnableScheduling
@RequiredArgsConstructor
@Slf4j
public class AdsScheduledService {
    private final CategoryParseListProperties categorySourceList;
    private final KufarClient kufarClient;
    private final NIOKufarClient nioKufarClient;
    private final AdvertisementService advertisementService;
    private final AdvertisementPostProcessService advertisementPostProcessService;
    private final SellerService sellerService;
    private final PublishService publishService;


    @Scheduled(initialDelay = 20_000, fixedDelay = 10_000)
    public void getNewAdsAndSaveIfNotExists() {

        categorySourceList.getCategories().stream()
                .parallel()
                .map(kufarClient::getNewAdsByCategoryIdAndByLastSort)
                .flatMap(adsDTO -> adsDTO.getAds().stream().parallel())
                .filter(Objects::nonNull)
                .filter(dto -> !advertisementService.existsByPublishedAt(dto.getListTime(), dto.getAdId(), dto.getCategory()))
                .map(adDTO -> {
                    String sellerId = adDTO.getAccountId();
                    Seller seller = new Seller(sellerId);
                    sellerService.save(seller);
                    Advertisement advertisement = Mapper.mapToEntity(adDTO);
                    advertisement.setSeller(seller);
                    adDTO.getAdParameters().stream()
                            .map(Mapper::mapToEntity)
                            .forEach(advertisement::addParameter);
                    return advertisement;
                })
                .forEach(advertisement -> {
                    nioKufarClient.getDetails(advertisement.getAdId())
                            .map(detailsDTO -> {
                                String details = detailsDTO.getResult().getBody();
                                advertisement.setDetails(details);
                                return advertisement;
                            })
                            .map(advertisementPostProcessService::postProcess)
                            .doOnSuccess(publishService::publish)
                            .doOnError(error -> log.error("Error: {}", error.getMessage()))
                            .subscribe();
                });
    }

}
