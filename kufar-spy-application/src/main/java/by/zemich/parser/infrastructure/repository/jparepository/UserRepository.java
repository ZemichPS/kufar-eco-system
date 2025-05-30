package by.zemich.parser.infrastructure.repository.jparepository;

import by.zemich.parser.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    @Override
    Optional<User> findById(UUID id);

    Optional<User> findByTelegramChatId(Long chatId);

}
