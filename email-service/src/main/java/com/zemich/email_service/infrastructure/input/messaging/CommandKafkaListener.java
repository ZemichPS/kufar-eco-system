package com.zemich.email_service.infrastructure.input.messaging;

import com.zemich.email_service.application.service.EmailServiceFacade;
import com.zemich.email_service.domain.command.SendActivationCodeCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.specific.SpecificData;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CommandKafkaListener {

    private final EmailServiceFacade emailServiceFacade;

    @KafkaListener(
            topics = "${kafka.topic.orders}",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void listen(@Payload GenericRecord record, Acknowledgment ack) {
        SendActivationCodeCommand command = (SendActivationCodeCommand) SpecificData.get().deepCopy(SendActivationCodeCommand.SCHEMA$, record);
        emailServiceFacade.sendActivationCode(command);
        ack.acknowledge();
    }
}
