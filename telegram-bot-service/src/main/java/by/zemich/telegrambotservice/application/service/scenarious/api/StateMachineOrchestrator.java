package by.zemich.telegrambotservice.application.service.scenarious.api;

import by.zemich.telegrambotservice.application.service.scenarious.ScenarioType;
import by.zemich.telegrambotservice.application.service.scenarious.adcreation.AdCreationState;
import by.zemich.telegrambotservice.application.service.scenarious.adcreation.AddAdvertisementEvent;
import by.zemich.telegrambotservice.application.service.scenarious.registration.event.UserRegistrationEvent;
import by.zemich.telegrambotservice.application.service.scenarious.registration.state.UserRegistrationState;
import by.zemich.telegrambotservice.domain.model.UserSession;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class StateMachineOrchestrator <S extends Enum<S>, E extends Enum<E>>{

    private final Map<ScenarioType, StateMachineFactory<?, ?>> fsmFactoryRegistry;
    private final StateMachinePersister<?, ?, String> persister;

    @SuppressWarnings("unchecked")
    public StateMachine<S, E> getStateMachineBySession(
            UserSession userSession
    ) {
        StateMachine<S, E> stateMachine;
        ScenarioType currentScenario = userSession.getCurrentScenarioType();

        if (userSession.getStateMachineId() == null) {
            try {
                UUID stateMachineId = UUID.randomUUID();
                stateMachine = (StateMachine<S, E>) fsmFactoryRegistry
                        .get(currentScenario)
                        .getStateMachine(stateMachineId);
                userSession.setStateMachineId(stateMachineId);
            } catch (Exception e) {
                log.error("Failed to create StateMachine", e);
                throw e;
            }
        } else {
            UUID stateMachineId = userSession.getStateMachineId();

            stateMachine = (StateMachine<S, E>) fsmFactoryRegistry
                    .get(userSession.getCurrentScenarioType())
                    .getStateMachine(userSession.getStateMachineId());
            persister.restore((StateMachine<?, ?>) stateMachine, stateMachineId.toString());
        }

        stateMachine.getExtendedState().getVariables().putAll(userSession.getContextData());
        return stateMachine;
    }
}
