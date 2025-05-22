package by.zemich.userservice.domain.service;

import by.zemich.userservice.domain.command.SendActivationCodeCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecord;
import org.apache.kafka.common.errors.TimeoutException;
import org.springframework.kafka.KafkaException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageService {

    private final KafkaTemplate<String, SpecificRecord> kafkaTemplate;

    public void sendActivationCode(SendActivationCodeCommand command) {
        Message<SpecificRecord> message = MessageBuilder
                .withPayload(command)
                .setHeader(KafkaHeaders.TOPIC, "userservice.commands")
                .build();

        sendMessage(message);
    }

    @Retryable(
            retryFor = {KafkaException.class, TimeoutException.class},
            maxAttempts = 3,
            backoff = @Backoff(delay = 1000, multiplier = 3)

    )
    private void sendMessage(Message<SpecificRecord> message) {
        kafkaTemplate.send(message);
    }

    @Recover
    public void handleRetryExhausted(Exception ex, String topic, String key, SpecificRecord value) {
        log.error("All retries failed for topic={}, key={}. Error: {}", topic, key, ex.getMessage());
            kafkaTemplate.send(topic + ".DLQ", key, value);
    }
}
