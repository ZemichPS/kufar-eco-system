package by.zemich.telegrambotservice.infrastructure.config.fsm;

import by.zemich.telegrambotservice.application.service.AddAdsStateMachineListenerAdapter;
import by.zemich.telegrambotservice.application.service.botscenarious.adcreation.AdCreationState;
import by.zemich.telegrambotservice.application.service.botscenarious.adcreation.AddAdvertisementEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import java.util.EnumSet;


@Configuration
@EnableStateMachineFactory(name = "ADD_ADVERTISEMENT")
@RequiredArgsConstructor
public class AddAdvertisementFSMConfig extends StateMachineConfigurerAdapter<AdCreationState, AddAdvertisementEvent> {

    private final AddAdsStateMachineListenerAdapter listenerAdapter;

    @Override
    public void configure(StateMachineStateConfigurer<AdCreationState, AddAdvertisementEvent> states) throws Exception {
        states.withStates()
                .initial(AdCreationState.START_AD_CREATION)
                .end(AdCreationState.CONFIRMATION)
                .states(EnumSet.allOf(AdCreationState.class));
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<AdCreationState, AddAdvertisementEvent> transitions) throws Exception {
        transitions
                .withExternal()
                    .source(AdCreationState.START_AD_CREATION)
                    .target(AdCreationState.CATEGORY_INPUT)
                    .event(AddAdvertisementEvent.STARTED)
                .and()
                .withExternal()
                    .source(AdCreationState.CATEGORY_INPUT)
                    .target(AdCreationState.CONDITION_INPUT)
                    .event(AddAdvertisementEvent.CATEGORY_RECEIVED)
                .and()
                .withExternal()
                    .source(AdCreationState.CONDITION_INPUT)
                    .target(AdCreationState.PRICE_INPUT)
                    .event(AddAdvertisementEvent.CONDITION_RECEIVED)
                    .action(stateContext -> stateContext.getExtendedState().getVariables().put("nextEvent", AddAdvertisementEvent.COMMENT_RECEIVED))
                .and()
                .withExternal()
                    .source(AdCreationState.PRICE_INPUT)
                    .target(AdCreationState.COMMENT_INPUT)
                    .event(AddAdvertisementEvent.PRICE_RECEIVED)
                .and()
                .withExternal()
                    .source(AdCreationState.COMMENT_INPUT)
                    .target(AdCreationState.PHOTO_INPUT)
                    .event(AddAdvertisementEvent.COMMENT_RECEIVED)
                .and()
                .withExternal()
                    .source(AdCreationState.PHOTO_INPUT)
                    .target(AdCreationState.ATTRIBUTES_INPUT)
                    .event(AddAdvertisementEvent.PHOTO_RECEIVED)
                .and()
                .withExternal()
                    .source(AdCreationState.ATTRIBUTES_INPUT)
                    .target(AdCreationState.CONFIRMATION)
                    .event(AddAdvertisementEvent.ATTRIBUTES_RECEIVED);

    }

    @Override
    public void configure(StateMachineConfigurationConfigurer<AdCreationState, AddAdvertisementEvent> config) throws Exception {
        config.withConfiguration().listener(listenerAdapter);
    }
}
