package by.zemich.telegrambotservice.infrastructure.config.fsm;

import by.zemich.telegrambotservice.application.service.FileLoader;
import by.zemich.telegrambotservice.application.service.scenarious.ScenarioType;
import by.zemich.telegrambotservice.application.service.scenarious.adcreation.AdCreationState;
import by.zemich.telegrambotservice.application.service.scenarious.adcreation.AddAdvertisementEvent;
import by.zemich.telegrambotservice.application.service.scenarious.registration.event.UserRegistrationEvent;
import by.zemich.telegrambotservice.application.service.scenarious.registration.state.UserRegistrationState;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.transition.Transition;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

@Configuration
@Log4j2
@RequiredArgsConstructor
public class FSMCommonConfig {

    @Bean
    public Map<ScenarioType, StateMachineFactory<?, ?>> fsmFactoryRegistry(
            StateMachineFactory<AdCreationState, AddAdvertisementEvent> addAdFSMFactory,
            StateMachineFactory<UserRegistrationState, UserRegistrationEvent> registrationFSMFactory
    ) {
        Map<ScenarioType, StateMachineFactory<?, ?>> registry = new EnumMap<>(ScenarioType.class);
        registry.put(ScenarioType.USER_REGISTRATION, registrationFSMFactory);
        registry.put(ScenarioType.ADD_ADVERTISEMENT, addAdFSMFactory);
        return registry;
    }

    @Bean
    public StateMachineListener<UserRegistrationState, UserRegistrationEvent> stateMachineListener() {
        return new StateMachineListenerAdapter<>() {
            @Override
            public void transition(Transition<UserRegistrationState, UserRegistrationEvent> transition) {
                log.info("FSM TRANSITION: {} -> {} via {}",
                        transition.getSource() != null ? transition.getSource().getId() : null,
                        transition.getTarget() != null ? transition.getTarget().getId() : null,
                        transition.getTrigger() != null ? transition.getTrigger().getEvent() : null);
            }

            @Override
            public void stateChanged(State<UserRegistrationState, UserRegistrationEvent> from, State<UserRegistrationState, UserRegistrationEvent> to) {
                log.info("FSM STATE CHANGED: {} -> {}", from != null ? from.getId() : "null", to.getId());
            }

            @Override
            public void stateMachineError(StateMachine<UserRegistrationState, UserRegistrationEvent> stateMachine, Exception exception) {
                log.error("FSM ERROR", exception);
            }


            @Override
            public void eventNotAccepted(Message<UserRegistrationEvent> event) {
                log.warn("FSM EVENT NOT ACCEPTED: {}", event.getPayload());
            }
        };
    }

}
