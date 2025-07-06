package by.zemich.telegrambotservice.infrastructure.config.fsm;

import by.zemich.telegrambotservice.application.service.scenarious.api.BaseRenderAction;
import by.zemich.telegrambotservice.application.service.scenarious.api.CustomGuard;
import by.zemich.telegrambotservice.application.service.scenarious.registration.action.handleinput.AbstractUserRegistrationInputHandlerAction;
import by.zemich.telegrambotservice.application.service.scenarious.registration.event.UserRegistrationEvent;
import by.zemich.telegrambotservice.application.service.scenarious.registration.state.UserRegistrationState;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import java.util.EnumSet;
import java.util.Map;

@Configuration
@EnableStateMachineFactory(name = "REGISTRATION")
@RequiredArgsConstructor
public class RegistrationFsmConfig extends StateMachineConfigurerAdapter<UserRegistrationState, UserRegistrationEvent> {

    private final Map<UserRegistrationState, BaseRenderAction<UserRegistrationState, UserRegistrationEvent>> registrationRenderActionMap;
    private final Map<UserRegistrationState, CustomGuard<UserRegistrationState, UserRegistrationEvent>> registrationGuardMap;
    private final Map<UserRegistrationState, AbstractUserRegistrationInputHandlerAction registrationInputhandlerMap;

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
                    .target(UserRegistrationState.USER_DATA_INPUT)
                    .event(UserRegistrationEvent.STARTED)
                    .action(registrationRenderActionMap.get(UserRegistrationState.START_REGISTRATION))
                .and()
                .withChoice()
                    .source(UserRegistrationState.USER_DATA_INPUT)
                    .first(UserRegistrationState.USER_DATA_INPUT,
                        registrationGuardMap.get(UserRegistrationState.USER_DATA_INPUT)
                    )
                    .last(UserRegistrationState.USER_EXISTS)
                .and()
                .withExternal()
                    .source(UserRegistrationState.USER_DATA_INPUT)
                    .target(UserRegistrationState.USER_CONTACT_INPUT)
                    .event(UserRegistrationEvent.USER_DATA_INPUTED)
                    .action(registrationInputhandlerMap.get(UserRegistrationState.USER_DATA_INPUT))
                .and()
                .withExternal()
                    .source(UserRegistrationState.USER_CONTACT_INPUT)
                    .target(UserRegistrationState.ROLE_INPUT)
                    .event(UserRegistrationEvent.USER_CONTACT_INPUTED)
                    .action(registrationInputhandlerMap.get(UserRegistrationState.USER_CONTACT_INPUT))
                .and()
                .withChoice()
                    .first(UserRegistrationState.ROLE_INPUT,
                            registrationGuardMap.get(UserRegistrationState.ROLE_INPUT)
                    )
                    .last(UserRegistrationState.ROLE_INPUT_ERROR)
                .and()
                .withExternal()
                    .source(UserRegistrationState.ROLE_INPUT)
                    .target(UserRegistrationState.CONFIRM_REGISTRATION)
                    .event()



    }
}
