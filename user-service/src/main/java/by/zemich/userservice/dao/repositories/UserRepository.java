package by.zemich.userservice.dao.repositories;

import by.zemich.userservice.dao.entities.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    boolean existsByEmail(String email);

    @Override
    Optional<User> findById(UUID uuid);

    Optional<User> findByTelegramUserId(String telegramId);

    Optional<User> findByEmail(String email);
}
