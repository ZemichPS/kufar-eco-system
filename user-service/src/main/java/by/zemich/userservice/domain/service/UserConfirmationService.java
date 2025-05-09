package by.zemich.userservice.domain.service;

import by.zemich.userservice.domain.exception.ConfirmationException;
import by.zemich.userservice.domain.model.code.entity.EmailConfirmationCode;
import by.zemich.userservice.domain.model.user.vo.UserId;
import by.zemich.userservice.domain.specification.ExpiredCodeSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserConfirmationService {

    private final EmailBodyProvider emailBodyProvider;
    private final ConfirmationCodeService confirmationCodeService;
    private final EmailService emailService;

    public void sendConfirmationEmail(String email, UserId userId) {
        EmailConfirmationCode emailConfirmationCode =
                confirmationCodeService.generateConfirmationCode(email, userId);
        String bodyMessage = emailBodyProvider.generateEmailApproveMessageBody(emailConfirmationCode.getCode());
        emailService.sendEmail(email, bodyMessage, "Подтверждение регистрации PartsFlow");
        confirmationCodeService.save(emailConfirmationCode);
    }


    public void checkConfirmationCode(UserId userId, String confirmationCode) {

        EmailConfirmationCode code = confirmationCodeService.findByUserIdAndCode(userId, confirmationCode)
                .orElseThrow(() -> new ConfirmationException("Invalid code"));
        new ExpiredCodeSpecification().check(code);
        confirmationCodeService.save(code);
    }

}
