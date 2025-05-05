package by.zemich.userservice.domain.repository;

import by.zemich.userservice.domain.models.code.entity.EmailConfirmationCode;
import by.zemich.userservice.domain.models.user.vo.UserId;

import java.util.Optional;

public interface EmailConfirmationCodeRepository {
    EmailConfirmationCode save(EmailConfirmationCode emailConfirmationCode);
    Optional<EmailConfirmationCode> findByEmail(String email);

    Optional<EmailConfirmationCode> findByUserId(UserId userId);
}
