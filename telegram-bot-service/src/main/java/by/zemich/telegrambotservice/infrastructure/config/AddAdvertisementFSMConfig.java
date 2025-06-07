package by.zemich.telegrambotservice.infrastructure.config;

import by.zemich.telegrambotservice.application.service.dialogs.FSM.AddAdvertisementEvent;
import by.zemich.telegrambotservice.application.service.dialogs.FSM.AddAdvertisementState;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;


@Configuration
@EnableStateMachineFactory(name = "addAdvertisementMachine")
public class AddAdvertisementFSMConfig extends StateMachineConfigurerAdapter<AddAdvertisementState, AddAdvertisementEvent> {
    @Override
    public void configure(StateMachineStateConfigurer<AddAdvertisementState, AddAdvertisementEvent> states) throws Exception {
        states.withStates()
                .initial(AddAdvertisementState.START)
                .state(AddAdvertisementState.WAIT_MANUFACTURE_CHOICE)
                .state(AddAdvertisementState.WAIT_CATEGORY_CHOICE)
                .end(AddAdvertisementState.FINISHED);
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<AddAdvertisementState, AddAdvertisementEvent> transitions) throws Exception {
        transitions.withExternal().source(AddAdvertisementState.START).target(AddAdvertisementState.WAIT_MANUFACTURE_CHOICE).event(AddAdvertisementEvent.START_ADDING)
                .and()
                .withExternal().source(AddAdvertisementState.WAIT_MANUFACTURE_CHOICE).target(AddAdvertisementState.WAIT_CATEGORY_CHOICE).event(AddAdvertisementEvent.MANUFACTURER_ENTERED)
                .and()
                .withExternal().source(AddAdvertisementState.WAIT_MANUFACTURE_CHOICE).target(AddAdvertisementState.FINISHED).event(AddAdvertisementEvent.MANUFACTURER_ENTERED);

    }
}
