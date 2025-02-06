package by.zemich.parser.infrastructure.scheduler;

import by.zemich.parser.application.service.*;
import by.zemich.parser.application.service.api.EventPublisher;
import by.zemich.parser.domain.model.Advertisement;
import by.zemich.parser.domain.model.Seller;
import by.zemich.parser.infrastructure.clients.KufarClient;
import by.zemich.parser.infrastructure.clients.NIOKufarClient;
import by.zemich.parser.infrastructure.properties.CategoryParseListProperties;
import by.zemich.parser.infrastructure.utils.Mapper;
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
    private final EventPublisher eventPublisher;

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
                            .doOnSuccess(eventPublisher::publish)
                            .doOnError(error -> log.error("Error: {}", error.getMessage()))
                            .subscribe();
                });
    }

}
