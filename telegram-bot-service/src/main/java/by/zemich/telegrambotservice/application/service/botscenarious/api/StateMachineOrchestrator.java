package by.zemich.telegrambotservice.application.service.botscenarious.api;

import by.zemich.telegrambotservice.application.service.botscenarious.ScenarioType;
import by.zemich.telegrambotservice.domain.model.UserSession;
import lombok.AllArgsConstructor;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@AllArgsConstructor
public class StateMachineOrchestrator {

    private final Map<ScenarioType, StateMachineFactory<?, ?>> fsmFactoryRegistry;

    @SuppressWarnings("unchecked")
    public <S extends Enum<S>, E extends Enum<E>> StateMachine<S, E> getStateMachine(
            UserSession userSession
    ) {
        StateMachine<S, E> stateMachine = (StateMachine<S, E>) fsmFactoryRegistry
                .get(userSession.getCurrentScenarioType())
                .getStateMachine(userSession.getStateMachineId());

        if (stateMachine == null) {
            stateMachine = (StateMachine<S, E>) fsmFactoryRegistry.get(userSession.getCurrentScenarioType()).getStateMachine();
            userSession.setStateMachineId(stateMachine.getId());
        }
        stateMachine.start();
        stateMachine.getExtendedState().getVariables().putAll(userSession.getContextData());
        return stateMachine;
    }


}
