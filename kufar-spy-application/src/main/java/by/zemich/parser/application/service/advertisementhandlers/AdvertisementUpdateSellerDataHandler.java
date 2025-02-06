package by.zemich.parser.application.service.advertisementhandlers;

import by.zemich.parser.application.service.SellerService;
import by.zemich.parser.application.service.advertisementhandlers.api.AdvertisementProcessor;
import by.zemich.parser.application.service.api.SellerWebClient;
import by.zemich.parser.domain.model.Advertisement;
import by.zemich.parser.domain.model.Seller;
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
