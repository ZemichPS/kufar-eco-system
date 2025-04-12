package by.zemich.userservice.service;

import by.zemich.userservice.dao.entities.User;
import by.zemich.userservice.dao.repositories.UserRepository;
import by.zemich.userservice.service.exceptions.UserExistsException;
import by.zemich.userservice.service.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User register(User user) {
        userRepository.findByEmail(user.getEmail()).ifPresent(
                existingUser -> {
                    throw new UserExistsException(existingUser.getEmail());
                });
        return userRepository.save(user);
    }


    public User getUserByTelegramId(String telegramId) {
        return userRepository.findByTelegramUserId(telegramId)
                .orElseThrow(() -> new UserNotFoundException(telegramId));
    }


}
