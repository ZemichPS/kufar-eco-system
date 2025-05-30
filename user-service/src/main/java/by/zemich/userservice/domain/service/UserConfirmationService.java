package by.zemich.userservice.domain.service;

import by.zemich.userservice.domain.command.SendActivationCodeCommand;
import by.zemich.userservice.domain.exception.ConfirmationException;
import by.zemich.userservice.domain.model.code.entity.EmailConfirmationCode;
import by.zemich.userservice.domain.model.user.vo.UserId;
import by.zemich.userservice.domain.specification.ExpiredCodeSpecification;
import by.zemich.userservice.infrastructure.messaging.kafka.KafkaProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserConfirmationService {

    private final ConfirmationCodeService confirmationCodeService;
    private final KafkaProducerService kafkaProducerService;


    public void sendConfirmationEmail(String email, UserId userId) {
        EmailConfirmationCode code =
                confirmationCodeService.generateConfirmationCode(email, userId);
        SendActivationCodeCommand command = new SendActivationCodeCommand(email, code.getCode());
        kafkaProducerService.send(command);
        confirmationCodeService.save(code);
    }


    public void checkConfirmationCode(UserId userId, String confirmationCode) {

        EmailConfirmationCode code = confirmationCodeService.findByUserIdAndCode(userId, confirmationCode)
                .orElseThrow(() -> new ConfirmationException("Invalid code"));
        new ExpiredCodeSpecification().check(code);
        confirmationCodeService.save(code);
    }

}
