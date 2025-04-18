package by.zemich.userservice.application.query;

import by.zemich.userservice.application.query.dto.GetUserByEmilQuery;
import by.zemich.userservice.domain.models.exceptions.UserNotFoundException;
import by.zemich.userservice.domain.models.queries.GetUserByIdQuery;
import by.zemich.userservice.domain.models.queries.GetUserByTelegramIdQuery;
import by.zemich.userservice.domain.models.user.entity.User;
import by.zemich.userservice.domain.models.user.vo.UserId;
import by.zemich.userservice.domain.repository.UserRepository;
import by.zemich.userservice.infrastructure.persistence.jpa.repositories.SpringDataUserRepository;
import by.zemich.userservice.infrastructure.persistence.jpa.repositories.UserViewRepository;
import by.zemich.userservice.infrastructure.persistence.jpa.repositories.projections.UserFullRecord;
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

    public UserFullRecord getFullRecordByEmail(GetUserByEmilQuery query) {
        return viewRepository.findByEmail(query.email())
                .orElseThrow(() -> new UserNotFoundException(query.email().toString()));
    }
}
