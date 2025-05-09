package by.zemich.userservice.infrastructure.persistence.jpa.repositories;

import by.zemich.userservice.domain.model.code.entity.EmailConfirmationCode;
import by.zemich.userservice.infrastructure.persistence.jpa.entities.EmailConfirmationCodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SpringDataEmailConfirmationRepository extends JpaRepository<EmailConfirmationCodeEntity, UUID> {

    Optional<EmailConfirmationCodeEntity> findByEmail(String email);

    Optional<EmailConfirmationCodeEntity> findByUserUuid(UUID userId);
    Optional<EmailConfirmationCodeEntity> findByUserUuidAndCode(UUID userId, String code);


}
