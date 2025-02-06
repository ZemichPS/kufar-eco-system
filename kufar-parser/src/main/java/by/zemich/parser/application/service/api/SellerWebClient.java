package by.zemich.parser.application.service.api;

import by.zemich.parser.domain.model.Seller;
import reactor.core.publisher.Mono;

public interface SellerWebClient {
    Mono<Seller> getSellerById(String id);
}
