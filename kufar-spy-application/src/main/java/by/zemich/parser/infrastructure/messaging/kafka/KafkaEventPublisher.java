package by.zemich.parser.infrastructure.messaging.kafka;

import by.zemich.parser.application.service.api.AdvertisementPublisher;
import by.zemich.parser.domain.model.Advertisement;
import by.zemich.parser.domain.model.events.AdvertisementCreatedEvent;
import by.zemich.parser.infrastructure.messaging.kafka.mapper.AdvertisementMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaEventPublisher implements AdvertisementPublisher {

    private final KafkaTemplate<String, SpecificRecord> kafkaTemplate;

    @Override
    public boolean publish(Advertisement advertisement) {
        AdvertisementCreatedEvent event = AdvertisementMapper.mapToEvent(advertisement);
        CompletableFuture<SendResult<String, SpecificRecord>> future = kafkaTemplate.send("advertisement-events", event);
        future.whenComplete((result, exception) -> {
            if (exception == null) {
                handleSuccess(result);
            } else {
                handleFailure(exception, advertisement.getId());
            }
        });
        return true;
    }

    private void handleSuccess(SendResult<String, SpecificRecord> sendResult) {
        SpecificRecord specificRecord = sendResult.getProducerRecord().value();
        String id = (String) specificRecord.get(0);
        log.info("Advertisement published to kafka event bus successfully. Advertisement id: {}", id);
    }

    private void handleFailure(Throwable throwable, UUID id) {
        log.error("Failed to sent advertisement event to eventbus. Message:{}. Advertisement id: {}", throwable.getMessage(), id);
    };
}
