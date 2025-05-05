package by.zemich.userservice.domain.service;

import by.zemich.userservice.domain.models.code.entity.EmailConfirmationCode;
import by.zemich.userservice.domain.models.code.vo.CodeId;
import by.zemich.userservice.domain.models.user.vo.UserId;
import by.zemich.userservice.domain.repository.EmailConfirmationCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ConfirmationCodeService {

    private final CodeGenerator codeGenerator;
    private final EmailConfirmationCodeRepository repository;

    public EmailConfirmationCode generateConfirmationCode(String email, UserId userId) {
        String code = codeGenerator.generateConfirmCode();
        return EmailConfirmationCode.builder()
                .codeId(new CodeId(UUID.randomUUID()))
                .userId(userId)
                .email(email)
                .code(code)
                .confirmed(false)
                .expiresAt(LocalDateTime.now().plusMinutes(30))
                .build();
    }

    public EmailConfirmationCode save(EmailConfirmationCode code) {
        return repository.save(code);
    }

    public Optional<EmailConfirmationCode> findByEmail(String email) {
        return repository.findByEmail(email);
    }

    public Optional<EmailConfirmationCode> findByUserId(UserId userId) {
        return repository.findByUserId(userId);
    }
}
