package by.zemich.advertisementservice.infrastracture.listeners;

import by.zemich.advertisementservice.infrastracture.events.AdvertisementCreatedEvent;
import by.zemich.advertisementservice.infrastracture.output.repository.elastic.documents.AdvertisementDocument;
import by.zemich.advertisementservice.infrastracture.output.repository.jpa.api.AdvertisementRepository;
import by.zemich.advertisementservice.infrastracture.output.repository.jpa.entity.AdvertisementAttributeEntity;
import by.zemich.advertisementservice.infrastracture.output.repository.jpa.entity.AdvertisementEntity;
import co.elastic.clients.elasticsearch._types.ElasticsearchException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.web.client.RestClientException;

import java.io.IOException;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AdvertisementIndexingListener {

    private final ElasticsearchOperations elasticsearchOperations;
    private final AdvertisementRepository advertisementRepository;


    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Retryable(
            retryFor = {
                    ElasticsearchException.class,
                    IOException.class,
                    RestClientException.class
            },
            maxAttempts = 3,
            backoff = @Backoff(delay = 1000, multiplier = 2)
    )
    public void onAdvertisementCreated(AdvertisementCreatedEvent event) {
        UUID advertisementId = UUID.randomUUID();
        // TODO заменить exception
        AdvertisementEntity advertisementEntity = advertisementRepository
                .findById(advertisementId)
                .orElseThrow(() -> new RuntimeException("Advertisement not found"));
        AdvertisementDocument document = mapToDocument(advertisementEntity);
        elasticsearchOperations.save(document);
    }

    private AdvertisementDocument mapToDocument(AdvertisementEntity entity) {
        return AdvertisementDocument.builder()
                .uuid(entity.getUuid())
                .categoryName(entity.getCategory().getName())
                .condition(entity.getCondition().getConditionDescription())
                .attributesValues(entity.getAttributes().stream()
                        .map(AdvertisementAttributeEntity::getValue)
                        .toList())
                .build();
    }
}
