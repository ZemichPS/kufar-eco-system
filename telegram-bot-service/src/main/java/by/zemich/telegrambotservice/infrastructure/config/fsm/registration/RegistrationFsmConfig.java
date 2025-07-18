package by.zemich.telegrambotservice.infrastructure.config.fsm.registration;

import by.zemich.telegrambotservice.application.service.scenarious.api.BaseRenderAction;
import by.zemich.telegrambotservice.application.service.scenarious.api.CustomGuard;
import by.zemich.telegrambotservice.application.service.scenarious.registration.action.handleinput.AbstractUserRegistrationInputHandlerAction;
import by.zemich.telegrambotservice.application.service.scenarious.registration.event.UserRegistrationEvent;
import by.zemich.telegrambotservice.application.service.scenarious.registration.state.UserRegistrationState;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.persist.StateMachineRuntimePersister;

import java.util.EnumSet;
import java.util.Map;

@Configuration
@EnableStateMachineFactory(name = "REGISTRATION")
@RequiredArgsConstructor
public class RegistrationFsmConfig extends StateMachineConfigurerAdapter<UserRegistrationState, UserRegistrationEvent> {

    private final StateMachineRuntimePersister<UserRegistrationState, UserRegistrationEvent, String> stateMachineRuntimePersister;
    private final StateMachineListener<UserRegistrationState, UserRegistrationEvent> stateMachineListener;

    private final Map<UserRegistrationState, BaseRenderAction<UserRegistrationState, UserRegistrationEvent>> renderActionMap;
    private final Map<UserRegistrationState, CustomGuard<UserRegistrationState, UserRegistrationEvent>> guardMap;
    private final Map<UserRegistrationState, AbstractUserRegistrationInputHandlerAction> inputhandlerMap;


    @Override
    public void configure(StateMachineConfigurationConfigurer<UserRegistrationState, UserRegistrationEvent> config) throws Exception {
        config.withConfiguration()
                .machineId("user-registration")
                .listener(stateMachineListener)
                .autoStartup(false)
                .and()
                .withPersistence().runtimePersister(stateMachineRuntimePersister);

    }

    @Override
    public void configure(StateMachineStateConfigurer<UserRegistrationState, UserRegistrationEvent> states) throws Exception {
        states.withStates()
                .initial(UserRegistrationState.START_REGISTRATION)
                .end(UserRegistrationState.END_REGISTRATION)
                .states(EnumSet.allOf(UserRegistrationState.class));
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<UserRegistrationState, UserRegistrationEvent> transitions) throws Exception {
        transitions
                .withExternal()
                    .source(UserRegistrationState.START_REGISTRATION)
                    .target(UserRegistrationState.GREETING)
                    .action(renderActionMap.get(UserRegistrationState.START_REGISTRATION))
                .and()
                .withExternal()
                    .source(UserRegistrationState.GREETING)
                    .target(UserRegistrationState.USER_CHECK_ROUTER)
                    .event(UserRegistrationEvent.CONTINUE_REGISTRATION)
                .and()
                    .withChoice()
                    .source(UserRegistrationState.USER_CHECK_ROUTER)
                .first(UserRegistrationState.USER_DATA_INPUT,
                        guardMap.get(UserRegistrationState.USER_DATA_INPUT)
                )
                .last(UserRegistrationState.USER_EXISTS)
                .and()
                .withExternal()
                .source(UserRegistrationState.USER_DATA_INPUT)
                .target(UserRegistrationState.USER_CONTACT_INPUT)
                .event(UserRegistrationEvent.DATA_SUBMITTED)
                .action(inputhandlerMap.get(UserRegistrationState.USER_DATA_INPUT))
                .action(renderActionMap.get(UserRegistrationState.USER_CONTACT_INPUT))
                .and()
                .withExternal()
                .source(UserRegistrationState.USER_EXISTS)
                .target(UserRegistrationState.NOTIFY_ABOUT_EXISTING_USER)
                .event(UserRegistrationEvent.USER_CHECKED_AND_EXISTS)
                .action(renderActionMap.get(UserRegistrationState.USER_EXISTS))
                .and()
                .withExternal()
                .source(UserRegistrationState.USER_CONTACT_INPUT)
                .target(UserRegistrationState.ROLE_INPUT)
                .event(UserRegistrationEvent.USER_CONTACT_RECEIVED)
                .action(inputhandlerMap.get(UserRegistrationState.USER_CONTACT_INPUT))
                .action(renderActionMap.get(UserRegistrationState.ROLE_INPUT))
                .and()
                .withChoice()
                .first(UserRegistrationState.ROLE_INPUT,
                        guardMap.get(UserRegistrationState.ROLE_INPUT)
                )
                .last(UserRegistrationState.ROLE_INPUT_ERROR)
                .and()
                .withExternal()
                .source(UserRegistrationState.ROLE_INPUT)
                .target(UserRegistrationState.CONFIRM_REGISTRATION)
                .event(UserRegistrationEvent.CONFIRM)
                .action(inputhandlerMap.get(UserRegistrationState.ROLE_INPUT))
                .action(renderActionMap.get(UserRegistrationState.CONFIRM_REGISTRATION))
        ;

    }

    private <E> void sendEvent(StateContext<?, E> context, E event) {
        context.getStateMachine().sendEvent(event);
    }
}
