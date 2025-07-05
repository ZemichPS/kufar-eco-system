package by.zemich.telegrambotservice.infrastructure.config.fsm;

import by.zemich.telegrambotservice.application.service.scenarious.adcreation.AdCreationState;
import by.zemich.telegrambotservice.application.service.scenarious.registration.event.UserRegistrationEvent;
import by.zemich.telegrambotservice.application.service.scenarious.registration.state.UserRegistrationState;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import java.util.EnumSet;

@Configuration
@EnableStateMachineFactory(name = "REGISTRATION")
@RequiredArgsConstructor
public class RegistrationFsmConfig extends StateMachineConfigurerAdapter<UserRegistrationState, UserRegistrationEvent> {
    @Override
    public void configure(StateMachineStateConfigurer<UserRegistrationState, UserRegistrationEvent> states) throws Exception {
        states.withStates()
                .initial(UserRegistrationState.START)
                .end(UserRegistrationState.END)
                .states(EnumSet.allOf(UserRegistrationState.class));
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<UserRegistrationState, UserRegistrationEvent> transitions) throws Exception {
        transitions
                .withChoice()
                .source(UserRegistrationState.START)
                .first()
                .last()
                .

    }
}
