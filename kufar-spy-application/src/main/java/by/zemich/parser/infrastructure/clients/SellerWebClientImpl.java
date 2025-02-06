package by.zemich.parser.infrastructure.clients;

import by.zemich.parser.application.service.api.SellerWebClient;
import by.zemich.parser.domain.model.Seller;
import by.zemich.parser.infrastructure.clients.dto.FeedbackResponse;
import by.zemich.parser.infrastructure.utils.Mapper;
import io.netty.handler.timeout.TimeoutException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.net.URI;
import java.time.Duration;

@Component
@Slf4j
@AllArgsConstructor
public class SellerWebClientImpl implements SellerWebClient {

    private final String FEEDBACKS = "https://feedbacks.kufar.by/feedback-query/v3/accounts/{sellerId}/feedbacks";
    private final WebClient webClient;

    @Override
    public Mono<Seller> getSellerById(String id) {
        URI uri = UriComponentsBuilder.fromHttpUrl(FEEDBACKS.replace("{sellerId}", id))
                .queryParam("offset", 0)
                .queryParam("limit", "100")
                .queryParam("sort_type", "created_desc")
                .build().toUri();


        return webClient.get()
                .uri(uri)
                .header("accept-language", "ru")
                .header("authority", "feedbacks.kufar.by")
                .header("authorization", "Bearer")
                .retrieve().bodyToMono(FeedbackResponse.class)
                .timeout(Duration.ofSeconds(10))
                .map(feedbackResponse -> Mapper.mapToEntity(feedbackResponse, id))
                .doOnError(throwable -> log.error(throwable.getMessage(), throwable))
                .retryWhen(Retry.backoff(20, Duration.ofMillis(1_000))
                        .maxBackoff(Duration.ofSeconds(1))
                )
                .doOnError(TimeoutException.class, ex -> log.error("Request timed out", ex));

    }
}
