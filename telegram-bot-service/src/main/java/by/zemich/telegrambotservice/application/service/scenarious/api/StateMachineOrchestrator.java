package by.zemich.telegrambotservice.application.service.scenarious.api;

import by.zemich.telegrambotservice.application.service.UpdateUtility;
import by.zemich.telegrambotservice.application.service.scenarious.ScenarioType;
import by.zemich.telegrambotservice.domain.model.UserSession;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Map;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class StateMachineOrchestrator<S extends Enum<S>, E extends Enum<E>> {

    private final Map<ScenarioType, StateMachineFactory<?, ?>> fsmFactoryRegistry;
    private final StateMachinePersister<?, ?, String> persister;

    @SuppressWarnings("unchecked")
    public StateMachine<S, E> createMachine(ScenarioType scenarioType) {
        StateMachine<S, E> stateMachine;
        UUID stateMachineId = UUID.randomUUID();
        try {
            stateMachine = (StateMachine<S, E>) fsmFactoryRegistry
                    .get(scenarioType)
                    .getStateMachine(stateMachineId);
        } catch (Exception e) {
            log.error("Failed to create StateMachine", e);
            throw e;
        }
        return stateMachine;
    }

    @SuppressWarnings("unchecked")
    public StateMachine<S, E> retrieveMachine(UUID stateMachineId, ScenarioType scenarioType) {
        StateMachine<S, E> stateMachine = (StateMachine<S, E>) fsmFactoryRegistry
                .get(scenarioType)
                .getStateMachine(stateMachineId);

        try {
            stateMachine = ((StateMachinePersister<S, E, String>) persister)
                    .restore(stateMachine, stateMachineId.toString());
        } catch (Exception e) {
            throw new RuntimeException("Filed in cast persister: " + e);
        }
        return stateMachine;
    }

    public void populateStateContext(
            StateMachine<S, E> stateMachine,
            Update update
    ) {
        String firsName = UpdateUtility.getUserFirstname(update).orElseThrow();
        String username = UpdateUtility.getUsername(update).orElseThrow();
        Long chatId = UpdateUtility.getChatId(update).orElseThrow();
        stateMachine.getExtendedState().getVariables().put("username", username);
        stateMachine.getExtendedState().getVariables().put("firsName", firsName);
        stateMachine.getExtendedState().getVariables().put("chatId", chatId);
    }
}
