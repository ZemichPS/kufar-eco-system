package by.zemich.kufar.application.service.api;

import by.zemich.kufar.domain.model.Seller;
import reactor.core.publisher.Mono;

public interface SellerWebClient {
    Mono<Seller> getSellerById(String id);
}
