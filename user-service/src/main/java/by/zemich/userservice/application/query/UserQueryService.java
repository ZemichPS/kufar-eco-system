package by.zemich.userservice.application.query;

import by.zemich.userservice.domain.exception.UserNotFoundException;
import by.zemich.userservice.domain.queries.ExistsUserByTelegramUserQuery;
import by.zemich.userservice.domain.queries.GetUserByEmilQuery;
import by.zemich.userservice.domain.queries.GetUserByTelegramIdQuery;
import by.zemich.userservice.domain.queries.GetUserByUserId;
import by.zemich.userservice.infrastructure.persistence.jpa.repositories.UserViewRepository;
import by.zemich.userservice.domain.dto.UserFullRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserQueryService {
    private final UserViewRepository viewRepository;

    public List<UserFullRecord> getAllRecords() {
        return viewRepository.findAllBy();
    }

    public UserFullRecord getByTelegramId(GetUserByTelegramIdQuery query) {
        String telegramId = query.telegramId();
        return viewRepository.findFullRecordByTelegramUserId(query.telegramId())
                .orElseThrow(() -> new UserNotFoundException(telegramId));
    }

    public UserFullRecord getFullRecordById(GetUserByUserId query) {
        return viewRepository.findRecordById(query.userId().id())
                .orElseThrow(() -> new UserNotFoundException(query.userId().id().toString()));
    }

    public UserFullRecord getFullRecordByEmail(GetUserByEmilQuery query) {
        return viewRepository.findByEmail(query.email())
                .orElseThrow(() -> new UserNotFoundException(query.email().toString()));
    }

    public boolean existsByTelegramUserId(ExistsUserByTelegramUserQuery query) {
       return viewRepository.existsByTelegramUserId(query.telegramUserId());
    }
}
