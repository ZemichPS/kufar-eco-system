package by.zemich.telegrambotservice.infrastructure.config.fsm;

import by.zemich.telegrambotservice.application.service.scenarious.api.BaseRenderAction;
import by.zemich.telegrambotservice.application.service.scenarious.api.CustomGuard;
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
                .withChoice()
                    .source(UserRegistrationState.START_REGISTRATION)
                    .first(UserRegistrationState.USER_DATA_INPUT,
                        registrationGuardMap.get(UserRegistrationState.USER_DATA_INPUT),
                        registrationRenderActionMap.get(UserRegistrationState.USER_DATA_INPUT)
                    )
                    .last(UserRegistrationState.USER_EXISTS)
                .and()
                .withExternal()
                    .source(UserRegistrationState.USER_DATA_INPUT)
                    .target(UserRegistrationState.USER_CONTACT_INPUT)
                    .action()
                    .action()
                    .guard()


    }
}
