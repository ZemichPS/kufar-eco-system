package by.zemich.telegrambotservice.infrastructure.config;

import by.zemich.telegrambotservice.application.service.AddAdsStateMachineListenerAdapter;
import by.zemich.telegrambotservice.application.service.dialogs.FSM.AddAdvertisementEvents;
import by.zemich.telegrambotservice.application.service.dialogs.FSM.AdCreationStates;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineModelConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;


@Configuration
@EnableStateMachineFactory(name = "ADD_ADVERTISEMENT")
@RequiredArgsConstructor
public class AddAdvertisementFSMConfig extends StateMachineConfigurerAdapter<AdCreationStates, AddAdvertisementEvents> {

    private final AddAdsStateMachineListenerAdapter listenerAdapter;

    @Override
    public void configure(StateMachineStateConfigurer<AdCreationStates, AddAdvertisementEvents> states) throws Exception {
        states.withStates()
                .initial(AdCreationStates.START)
                .state(AdCreationStates.CATEGORY_INPUT)
                .state(AdCreationStates.CONDITION_INPUT)
                .state(AdCreationStates.PRICE_INPUT)
                .state(AdCreationStates.COMMENT_INPUT)
                .state(AdCreationStates.PHOTO_INPUT)
                .state(AdCreationStates.ATTRIBUTES_INPUT)
                .state(AdCreationStates.CONFIRMATION)
                .end(AdCreationStates.COMPLETED);
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<AdCreationStates, AddAdvertisementEvents> transitions) throws Exception {
        transitions
                .withExternal()
                    .source(AdCreationStates.START).target(AdCreationStates.CATEGORY_INPUT)
                    .event(AddAdvertisementEvents.CATEGORY_RECEIVED)
                .and()
                .withExternal()
                    .source(AdCreationStates.CATEGORY_INPUT).target(AdCreationStates.CONDITION_INPUT)
                    .event(AddAdvertisementEvents.CONDITION_RECEIVED)
                .and()
                .withExternal()
                    .source(AdCreationStates.CONDITION_INPUT).target(AdCreationStates.PRICE_INPUT)
                    .event(AddAdvertisementEvents.PRICE_RECEIVED)
                .and()
                .withExternal()
                    .source(AdCreationStates.PRICE_INPUT).target(AdCreationStates.COMMENT_INPUT)
                    .event(AddAdvertisementEvents.COMMENT_RECEIVED)
                .and()
                .withExternal()
                    .source(AdCreationStates.COMMENT_INPUT).target(AdCreationStates.PHOTO_INPUT)
                    .event(AddAdvertisementEvents.PHOTO_RECEIVED)
                .and()
                .withExternal()
                    .source(AdCreationStates.PHOTO_INPUT).target(AdCreationStates.ATTRIBUTES_INPUT)
                    .event(AddAdvertisementEvents.ATTRIBUTES_RECEIVED)
                .and()
                .withExternal()
                    .source(AdCreationStates.ATTRIBUTES_INPUT).target(AdCreationStates.CONFIRMATION)
                    .event(AddAdvertisementEvents.CONFIRM);

    }

    @Override
    public void configure(StateMachineConfigurationConfigurer<AdCreationStates, AddAdvertisementEvents> config) throws Exception {
        config.withConfiguration().listener(listenerAdapter);
    }
}
