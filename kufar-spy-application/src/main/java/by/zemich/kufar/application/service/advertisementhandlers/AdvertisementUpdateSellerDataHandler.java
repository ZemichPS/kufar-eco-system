package by.zemich.kufar.application.service.advertisementhandlers;

import by.zemich.kufar.application.service.SellerService;
import by.zemich.kufar.application.service.advertisementhandlers.api.AdvertisementProcessor;
import by.zemich.kufar.application.service.api.SellerWebClient;
import by.zemich.kufar.domain.model.Advertisement;
import by.zemich.kufar.domain.model.Seller;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

//@Component
@Slf4j
@AllArgsConstructor
public class AdvertisementUpdateSellerDataHandler implements AdvertisementProcessor {
    private final SellerWebClient sellerWebClient;
    private final SellerService sellerService;

    @Override
    public void process(Advertisement advertisement) {
        Seller seller = advertisement.getSeller();
        String sellerId = seller.getId();
        sellerWebClient.getSellerById(sellerId)
                .doOnError(
                        throwable -> log.error("Error while updating seller. Error: {}", throwable.getMessage())
                )
                .subscribe(seller1 -> {
                    sellerService.save(seller1);
                    advertisement.setSeller(seller);
                })
        ;
    }

    @Override
    public boolean canProcess(Advertisement advertisement) {
        return advertisement.getSeller() != null;
    }
}
