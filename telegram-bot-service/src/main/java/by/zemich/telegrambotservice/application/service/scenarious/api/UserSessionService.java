package by.zemich.telegrambotservice.application.service.scenarious.api;

import by.zemich.telegrambotservice.application.service.scenarious.ScenarioType;
import by.zemich.telegrambotservice.domain.model.UserSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserSessionService {

    private final RedisTemplate<String, UserSession> redisTemplate;

    public Optional<UserSession> getByChatIdAndScenarioType(Long chatId, ScenarioType scenarioType) {
        String key = createKey(chatId, scenarioType);
        UserSession session = redisTemplate.opsForValue().get(key);
        return Optional.ofNullable(session);
    }

    public UserSession create(Long chatId, ScenarioType scenarioType) {
        UserSession session = UserSession.builder()
                .id(UUID.randomUUID())
                .chatId(chatId)
                .currentScenarioType(scenarioType)
                .contextData(HashMap.newHashMap(16))
                .lastActivity(Instant.now())
                .chatId(chatId)
                .build();
        String key = createKey(chatId, scenarioType);
        redisTemplate.opsForValue().set(key, session, Duration.ofHours(1));
        return session;
    }

    public void save(UserSession session, Long chatId) {
        String key = createKey(chatId, session.getCurrentScenarioType());
        redisTemplate.opsForValue().set(key, session, Duration.ofHours(1));
    }

    public Optional<UserSession> findByChatId(Long chatId) {
        String pattern = createKey(chatId, null) + "*";
        Set<String> keys = redisTemplate.keys(pattern);
        if (keys == null || keys.isEmpty()) {
            return Optional.empty();
        }
        String firstKey = keys.iterator().next();
        return Optional.ofNullable(redisTemplate.opsForValue().get(firstKey));

    }

    private String createKey(Long chatId, ScenarioType scenarioType) {
        return "user:" + chatId + ":" +
                (scenarioType != null ? scenarioType.name() : "");
    }

}
