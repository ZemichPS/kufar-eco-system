package by.zemich.telegrambotservice.application.service.dialogbotapi;

import by.zemich.telegrambotservice.application.service.botscenarious.ScenarioType;
import by.zemich.telegrambotservice.domain.model.UserSession;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;

@Service
@AllArgsConstructor
public class StateMachineOrchestrator {

    private final RedisTemplate<String, UserSession> redisTemplate;
    private final Map<ScenarioType, StateMachineFactory<?, ?>> factories;
    private final UserSessionService sessionService;
    private final UserSessionService userSessionService;


    public <S extends Enum<S>, E extends Enum<E>> void handleEvent(
            Long chatId,
            ScenarioType scenarioType,
            E event
    ) {
        UserSession session = sessionService.getByChatIdAndScenarioType(chatId, scenarioType)
                .orElseGet(() -> userSessionService.create(chatId, scenarioType));

        StateMachine<S, E> stateMachine = getStateMachine(session);
        stateMachine.sendEvent(event);
        session.setLastActivity(Instant.now());
        sessionService.save(session, chatId);
    }

    private <S, E> StateMachine<S, E> getStateMachine(UserSession session) {
        StateMachine<S, E> stateMachine = (StateMachine<S, E>) factories
                .get(session.getCurrentScenarioType())
                .getStateMachine(session.getStateMachineId());

        if (stateMachine == null) {
            stateMachine = (StateMachine<S, E>) factories.get(session.getCurrentScenarioType()).getStateMachine();
            session.setStateMachineId(stateMachine.getId());
            stateMachine.start();
        }
        stateMachine.getExtendedState().getVariables().putAll(session.getContextData());
        return stateMachine;
    }

}
