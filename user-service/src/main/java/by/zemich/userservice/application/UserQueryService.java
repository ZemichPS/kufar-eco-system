package by.zemich.userservice.application;

import by.zemich.userservice.domain.models.exceptions.UserNotFoundException;
import by.zemich.userservice.domain.models.queries.GetUserByIdQuery;
import by.zemich.userservice.domain.models.queries.GetUserByTelegramIdQuery;
import by.zemich.userservice.domain.models.user.entity.User;
import by.zemich.userservice.domain.models.user.vo.UserId;
import by.zemich.userservice.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserQueryService {
    private final UserRepository userRepository;

    public User getByTelegramId(GetUserByTelegramIdQuery query) {
        String telegramId = query.telegramId();
        return userRepository.getByTelegramId(query.telegramId())
                .orElseThrow(() -> new UserNotFoundException(telegramId));
    }

    public List<User> getAll() {
        return userRepository.getAll();
    }

    public User getById(GetUserByIdQuery query) {
        return userRepository.getUserId(new UserId(query.userId()))
                .orElseThrow(() -> new UserNotFoundException(query.userId().toString()));
    }
}
