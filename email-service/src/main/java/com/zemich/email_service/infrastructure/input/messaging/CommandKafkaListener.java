package com.zemich.email_service.infrastructure.input.messaging;

import com.zemich.email_service.application.service.EmailServiceFacade;
import com.zemich.email_service.domain.command.SendActivationCodeCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.generic.GenericRecord;
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
            groupId = "email-service-group",
            topics = "users.registration.approve",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void listen(@Payload GenericRecord record, Acknowledgment ack) {
        String to = record.get("to").toString();
        String code = record.get("code").toString();
        SendActivationCodeCommand command = new SendActivationCodeCommand(to, code);
        emailServiceFacade.sendActivationCode(command);
        ack.acknowledge();
    }
}
