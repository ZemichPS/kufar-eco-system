package by.zemich.telegrambotservice.application.service;

import by.zemich.telegrambotservice.application.service.scenarious.adcreation.AdCreationState;
import by.zemich.telegrambotservice.application.service.scenarious.adcreation.AddAdvertisementEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.transition.Transition;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class AddAdsStateMachineListenerAdapter extends StateMachineListenerAdapter<AdCreationState, AddAdvertisementEvent> {

    @Override
    public void transition(Transition<AdCreationState, AddAdvertisementEvent> transition) {
        log.info("Переход из {} в {}", transition.getSource(), transition.getTarget());
        transition.getTrigger().getEvent().name();
    }

}
