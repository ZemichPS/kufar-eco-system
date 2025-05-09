package by.zemich.userservice.domain.repository;

import by.zemich.userservice.domain.model.code.entity.EmailConfirmationCode;
import by.zemich.userservice.domain.model.user.vo.UserId;

import java.util.List;
import java.util.Optional;

public interface EmailConfirmationCodeRepository {
    EmailConfirmationCode save(EmailConfirmationCode emailConfirmationCode);

    Optional<EmailConfirmationCode> findByEmail(String email);

    Optional<EmailConfirmationCode> findByUserId(UserId userId);

    Optional<EmailConfirmationCode> findByUserIdAndCode(UserId userId, String confirmationCode);
}
