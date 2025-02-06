package by.zemich.telegrambotservice.application.service;

import by.zemich.telegrambotservice.application.service.api.AdvertisementPublisher;
import by.zemich.telegrambotservice.domain.model.KufarAdvertisement;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class PublishService {
    private final List<AdvertisementPublisher> advertisementPublishers;

    public void publish(KufarAdvertisement ad) {
        advertisementPublishers.forEach(publisher -> {
            Mono.defer(() -> Mono.fromRunnable(()-> publisher.publish(ad)))
                    .retryWhen(
                            Retry.backoff(20, Duration.ofSeconds(15))
                                    .maxBackoff(Duration.ofSeconds(20))
                                    .filter(this::allowRetry)
                                    .doBeforeRetry(retrySignal ->
                                            log.warn("Retry to send advertisement... attempt {}, cause: {}", retrySignal.totalRetries(), retrySignal.failure().getMessage()))
                    )
                    .doOnError(e -> log.error("Failed to publish advertisement with id: {}", ad.getAdId(), e))
                    .subscribe();
        });
    }

    private boolean allowRetry(Throwable throwable) {
        boolean allowed = throwable instanceof RuntimeException || throwable instanceof TelegramApiException;
        if (!allowed) log.warn("Exception not allowed for trying publish. Cause: {}", throwable.getMessage());
        return allowed;
    }


}
