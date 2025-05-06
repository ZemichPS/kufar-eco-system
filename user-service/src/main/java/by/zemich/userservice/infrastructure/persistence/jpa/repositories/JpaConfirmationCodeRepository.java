package by.zemich.userservice.infrastructure.persistence.jpa.repositories;

import by.zemich.userservice.domain.models.code.entity.EmailConfirmationCode;
import by.zemich.userservice.domain.models.user.vo.UserId;
import by.zemich.userservice.domain.repository.EmailConfirmationCodeRepository;
import by.zemich.userservice.infrastructure.persistence.jpa.entities.EmailConfirmationCodeEntity;
import by.zemich.userservice.infrastructure.persistence.jpa.mapper.CodeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JpaConfirmationCodeRepository implements EmailConfirmationCodeRepository {

    private final SpringDataEmailConfirmationRepository emailConfirmationRepository;

    @Override
    public EmailConfirmationCode save(EmailConfirmationCode emailConfirmationCode) {
        EmailConfirmationCodeEntity codeEntity = CodeMapper.mapToEntity(emailConfirmationCode);
        return CodeMapper.mapToDomain(emailConfirmationRepository.save(codeEntity));
    }

    @Override
    public Optional<EmailConfirmationCode> findByEmail(String email) {
        return emailConfirmationRepository.findByEmail(email).map(CodeMapper::mapToDomain);
    }

    @Override
    public Optional<EmailConfirmationCode> findByUserId(UserId userId) {
        return emailConfirmationRepository.findByUserUuid(userId.getId())
                .map(CodeMapper::mapToDomain);
    }


}
