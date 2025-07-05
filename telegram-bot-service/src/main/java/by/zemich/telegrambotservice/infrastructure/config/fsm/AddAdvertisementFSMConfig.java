package by.zemich.telegrambotservice.infrastructure.config.fsm;

import by.zemich.telegrambotservice.application.service.AddAdsStateMachineListenerAdapter;
import by.zemich.telegrambotservice.application.service.scenarious.adcreation.AdCreationState;
import by.zemich.telegrambotservice.application.service.scenarious.adcreation.AddAdvertisementEvent;
import by.zemich.telegrambotservice.application.service.scenarious.adcreation.action.AdCreationGoBackAction;
import by.zemich.telegrambotservice.application.service.scenarious.api.BaseRenderAction;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import java.util.EnumSet;
import java.util.Map;


@Configuration
@EnableStateMachineFactory(name = "ADD_ADVERTISEMENT")
@RequiredArgsConstructor
public class AddAdvertisementFSMConfig extends StateMachineConfigurerAdapter<AdCreationState, AddAdvertisementEvent> {

    private final AddAdsStateMachineListenerAdapter listenerAdapter;
    private final AdCreationGoBackAction adCreationGoBackAction;
    private final Map<AdCreationState, BaseRenderAction<AdCreationState, AddAdvertisementEvent>> renderActionMap;


    @Override
    public void configure(StateMachineStateConfigurer<AdCreationState, AddAdvertisementEvent> states) throws Exception {
        states.withStates()
                .initial(AdCreationState.START_AD_CREATION)
                .end(AdCreationState.CONFIRMATION)
                .states(EnumSet.allOf(AdCreationState.class))
                .stateEntry(AdCreationState.START_AD_CREATION, renderActionMap.get(AdCreationState.START_AD_CREATION), adCreationGoBackAction)
                .stateEntry(AdCreationState.SIDE_INPUT, renderActionMap.get(AdCreationState.SIDE_INPUT), adCreationGoBackAction)
                .stateEntry(AdCreationState.CATEGORY_INPUT, renderActionMap.get(AdCreationState.CATEGORY_INPUT), adCreationGoBackAction)
                .stateEntry(AdCreationState.CONDITION_INPUT, renderActionMap.get(AdCreationState.CONDITION_INPUT), adCreationGoBackAction)
                .stateEntry(AdCreationState.PRICE_INPUT, renderActionMap.get(AdCreationState.PRICE_INPUT), adCreationGoBackAction)
                .stateEntry(AdCreationState.COMMENT_INPUT, renderActionMap.get(AdCreationState.COMMENT_INPUT), adCreationGoBackAction)
                .stateEntry(AdCreationState.PHOTO_INPUT, renderActionMap.get(AdCreationState.PHOTO_INPUT), adCreationGoBackAction)
                .stateEntry(AdCreationState.ATTRIBUTES_INPUT, renderActionMap.get(AdCreationState.ATTRIBUTES_INPUT), adCreationGoBackAction)
                .stateEntry(AdCreationState.MANUFACTURER_INPUT, renderActionMap.get(AdCreationState.MANUFACTURER_INPUT), adCreationGoBackAction)
                .stateEntry(AdCreationState.MODEL_INPUT, renderActionMap.get(AdCreationState.MODEL_INPUT), adCreationGoBackAction)
                .stateEntry(AdCreationState.CONFIRMATION, renderActionMap.get(AdCreationState.CONFIRMATION), adCreationGoBackAction);
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
                .action(renderActionMap.get(AdCreationState.COMMENT_INPUT))
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
