package by.zemich.userservice.domain.service;

import by.zemich.userservice.domain.models.code.entity.EmailConfirmationCode;
import by.zemich.userservice.domain.models.user.vo.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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
        // TODO бросить кастомное бизнесовое исключение
        EmailConfirmationCode code = confirmationCodeService.findByUserId(userId).orElseThrow();
        if (LocalDateTime.now().isBefore(code.getExpiresAt())) throw new RuntimeException("Code expired");
        if (code.getCode().equals(confirmationCode)) throw new RuntimeException("Invalid confirmation code");
        code.setConfirmed(true);
        confirmationCodeService.save(code);
    }


}
