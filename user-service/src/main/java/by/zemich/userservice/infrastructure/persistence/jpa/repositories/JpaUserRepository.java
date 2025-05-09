package by.zemich.userservice.infrastructure.persistence.jpa.repositories;

import by.zemich.userservice.domain.model.user.entity.User;
import by.zemich.userservice.domain.model.user.vo.Email;
import by.zemich.userservice.domain.model.user.vo.UserId;
import by.zemich.userservice.domain.repository.UserRepository;
import by.zemich.userservice.infrastructure.persistence.jpa.entities.UserEntity;
import by.zemich.userservice.infrastructure.persistence.jpa.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JpaUserRepository implements UserRepository {

    private final SpringDataUserRepository springDataUserRepository;

    @Override
    public User save(User user) {
        UserEntity userEntityForSave = UserMapper.map(user);
        UserEntity savedUser = springDataUserRepository.save(userEntityForSave);
        return UserMapper.map(savedUser);
    }

    @Override
    public Optional<User> findByEmail(Email email) {
        return springDataUserRepository.findByEmail(email.getEmail())
                .map(UserMapper::map);
    }

    @Override
    public Optional<User> findById(UserId userId) {
        return springDataUserRepository.findById(userId.getId()).map(UserMapper::map);
    }

    @Override
    public boolean existsById(UserId userId) {
        return springDataUserRepository.existsById(userId.getId());
    }

    @Override
    public Optional<User> getByTelegramId(String telegramId) {
        return springDataUserRepository.findByTelegramUserId(telegramId).map(UserMapper::map);
    }

    @Override
    public List<User> getAll() {
        return springDataUserRepository.findAll().stream().map(UserMapper::map).toList();
    }

    @Override
    public Optional<User> getUserId(UserId userId) {
        return springDataUserRepository.findById(userId.getId()).map(UserMapper::map);
    }
}
