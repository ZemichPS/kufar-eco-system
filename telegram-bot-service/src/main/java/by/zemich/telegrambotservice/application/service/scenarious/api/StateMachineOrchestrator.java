package by.zemich.telegrambotservice.application.service.scenarious.api;

import by.zemich.telegrambotservice.application.service.scenarious.ScenarioType;
import by.zemich.telegrambotservice.application.service.scenarious.adcreation.AdCreationState;
import by.zemich.telegrambotservice.application.service.scenarious.adcreation.AddAdvertisementEvent;
import by.zemich.telegrambotservice.application.service.scenarious.registration.event.UserRegistrationEvent;
import by.zemich.telegrambotservice.application.service.scenarious.registration.state.UserRegistrationState;
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
    public <S extends Enum<S>, E extends Enum<E>> StateMachine<S, E> getStateMachineBySession(
            UserSession userSession
    ) {

        StateMachine<S, E> stateMachine;
        ScenarioType currentScenario = userSession.getCurrentScenarioType();

        if (userSession.getStateMachineId() == null) {
            stateMachine = (StateMachine<S, E>) fsmFactoryRegistry.get(currentScenario).getStateMachine();
            userSession.setStateMachineId(stateMachine.getId());
        } else {
            stateMachine = (StateMachine<S, E>) fsmFactoryRegistry
                    .get(userSession.getCurrentScenarioType())
                    .getStateMachine(userSession.getStateMachineId());
        }

        stateMachine.getExtendedState().getVariables().putAll(userSession.getContextData());
        return stateMachine;
    }


}
