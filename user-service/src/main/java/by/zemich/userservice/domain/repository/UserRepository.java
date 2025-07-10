package by.zemich.userservice.domain.repository;

import by.zemich.userservice.domain.model.user.entity.User;
import by.zemich.userservice.domain.model.user.vo.Email;
import by.zemich.userservice.domain.model.user.vo.UserId;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    User save(User user);

    Optional<User> findByEmail(Email email);

    Optional<User> findById(UserId userId);

    boolean existsById(UserId userId);
    boolean existsByEmail(String email);

    Optional<User> getByTelegramId(String telegramId);

    List<User> getAll();

    Optional<User> getUserId(UserId userId);

    boolean existsByTelegramId(Long telegramUserId);
}
