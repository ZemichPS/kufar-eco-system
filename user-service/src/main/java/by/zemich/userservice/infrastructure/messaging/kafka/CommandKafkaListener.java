package by.zemich.userservice.infrastructure.messaging.kafka;

import by.zemich.userservice.domain.command.RegisterUserCommand;
import by.zemich.userservice.domain.model.user.vo.Email;
import by.zemich.userservice.domain.model.user.vo.FullName;
import by.zemich.userservice.domain.model.user.vo.PhoneNumber;
import by.zemich.userservice.domain.model.user.vo.Role;
import lombok.RequiredArgsConstructor;
import org.apache.avro.generic.GenericRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import by.zemich.userservice.application.command.UserCommandService;


@Component
@RequiredArgsConstructor
public class CommandKafkaListener {

    private final UserCommandService userCommandService;

    @KafkaListener(
            groupId = "user-service-group",
            topics = "users.registration.registrare",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void listen(@Payload GenericRecord record, Acknowledgment ack) {
        RegisterUserCommand command = new RegisterUserCommand(
                new FullName(
                        record.get("firstName").toString(),
                        record.get("lastName").toString()
                ),
                Role.valueOf(record.get("role").toString()),
                new Email(record.get("email").toString()),
                new PhoneNumber(record.get("phoneNumber").toString()),
                record.get("password").toString()
        );
        userCommandService.handle(command);
        ack.acknowledge();
    }
}
