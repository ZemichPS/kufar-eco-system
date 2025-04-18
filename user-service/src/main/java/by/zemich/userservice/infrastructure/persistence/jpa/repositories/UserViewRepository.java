package by.zemich.userservice.infrastructure.persistence.jpa.repositories;

import by.zemich.userservice.infrastructure.persistence.jpa.entities.UserEntity;
import by.zemich.userservice.infrastructure.persistence.jpa.repositories.projections.UserFullRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserViewRepository extends JpaRepository<UserEntity, UUID> {

    List<UserFullRecord> findAllBy();

    Optional<UserFullRecord> findFullRecordByTelegramUserId(String telegramId);

    Optional<UserFullRecord> findByEmail(String email);
}
