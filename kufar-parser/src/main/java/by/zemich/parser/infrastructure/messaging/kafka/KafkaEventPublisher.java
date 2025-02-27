package by.zemich.parser.infrastructure.messaging.kafka;

import by.zemich.parser.application.service.EventService;
import by.zemich.parser.application.service.api.EventPublisher;
import by.zemich.parser.domain.model.Advertisement;
import by.zemich.parser.domain.model.events.AdvertisementCreatedEvent;
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
public class KafkaEventPublisher implements EventPublisher {
    private final KafkaTemplate<String, SpecificRecord> kafkaTemplate;
    private final EventService eventService;

    public void publish(Advertisement advertisement) {
        AdvertisementCreatedEvent event = eventService.create(advertisement);
        log.info("device func: {}", event.getFullyFunctional());
        String key = advertisement.getId().toString();
        CompletableFuture<SendResult<String, SpecificRecord>> future = kafkaTemplate.send("advertisement.created", key, event);
        future.whenComplete((result, exception) -> {
            if (exception == null) {
                handleSuccess(result);
            } else {
                handleFailure(exception, advertisement.getId());
            }
        });
    }

    private void handleSuccess(SendResult<String, SpecificRecord> sendResult) {
        SpecificRecord specificRecord = sendResult.getProducerRecord().value();
        String id = (String) specificRecord.get(0);
        log.info("Advertisement published to kafka event bus successfully. Advertisement id: {}", id);
    }

    private void handleFailure(Throwable throwable, UUID id) {
        log.error("Failed to sent advertisement event to eventbus. Message:{}. Advertisement id: {}", throwable.getMessage(), id);
    }


}
