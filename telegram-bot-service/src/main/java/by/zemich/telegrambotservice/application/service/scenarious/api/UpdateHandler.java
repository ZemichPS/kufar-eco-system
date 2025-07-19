package by.zemich.telegrambotservice.application.service.scenarious.api;

import by.zemich.telegrambotservice.application.service.UpdateUtility;
import by.zemich.telegrambotservice.application.service.scenarious.ScenarioType;
import by.zemich.telegrambotservice.application.service.scenarious.registration.event.UserRegistrationEvent;
import by.zemich.telegrambotservice.domain.model.UserSession;
import lombok.RequiredArgsConstructor;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UpdateHandler {

    private final StateMachineOrchestrator stateMachineOrchestrator;
    private final ScenarioDetector scenarioDetector;
    private final UserSessionService userSessionService;

    @SuppressWarnings("unchecked")
    public <E extends Enum<E>, S extends Enum<S>> void handle(Update update) {

        Long chatId = UpdateUtility.getChatId(update).orElseThrow();
        Long userId = UpdateUtility.getUserId(update).orElseThrow();
        ScenarioType scenarioType;
        StateMachine<S, E> stateMachine;

        UserSession session = userSessionService.findByUserId(userId)
                .orElseGet(() -> userSessionService.create(chatId, userId));

        if (session.getCurrentScenarioType() == null) {
            scenarioType = scenarioDetector.detectScenario(update, session);
            session.setCurrentScenarioType(scenarioType);
        } else scenarioType = session.getCurrentScenarioType();


        if (session.getStateMachineId() == null) {
            stateMachine = stateMachineOrchestrator.createMachine(scenarioType);
            stateMachineOrchestrator.populateStateContext(stateMachine, update);
            session.setStateMachineId(stateMachine.getUuid());
            userSessionService.update(session);
        } else {
            UUID stateMachineId = session.getStateMachineId();
            stateMachine = stateMachineOrchestrator.retrieveMachine(stateMachineId, scenarioType);
        }

        stateMachine.start();
        E event = (E) UpdateUtility.extractEventFromCallback(update, UserRegistrationEvent.class).orElseThrow();

    }
}
