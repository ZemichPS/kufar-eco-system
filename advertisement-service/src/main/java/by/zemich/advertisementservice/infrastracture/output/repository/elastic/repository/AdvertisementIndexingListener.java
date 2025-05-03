package by.zemich.advertisementservice.infrastracture.output.repository.elastic.repository;

import by.zemich.advertisementservice.infrastracture.events.AdvertisementCreatedEvent;
import by.zemich.advertisementservice.infrastracture.output.repository.elastic.documents.AdvertisementDocument;
import by.zemich.advertisementservice.infrastracture.output.repository.elastic.mapper.AdvertisementMapper;
import by.zemich.advertisementservice.infrastracture.output.repository.jpa.api.AdvertisementRepository;
import by.zemich.advertisementservice.infrastracture.output.repository.jpa.entity.AdvertisementEntity;
import co.elastic.clients.elasticsearch._types.analysis.UaxEmailUrlTokenizer;
import lombok.RequiredArgsConstructor;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AdvertisementIndexingListener {

    private final ElasticsearchOperations elasticsearchOperations;
    private final AdvertisementRepository advertisementRepository;


    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onAdvertisementCreated(AdvertisementCreatedEvent event) {
        UUID advertisementId = UUID.randomUUID();
        // TODO заменить exception
        AdvertisementEntity advertisementEntity = advertisementRepository
                .findById(advertisementId)
                .orElseThrow(() -> new RuntimeException("Advertisement not found"));
        AdvertisementDocument document = AdvertisementMapper.map(advertisementEntity);
        elasticsearchOperations.save(document);
    }
}
