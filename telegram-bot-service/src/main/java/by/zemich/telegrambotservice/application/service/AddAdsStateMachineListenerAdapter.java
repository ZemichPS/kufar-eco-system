package by.zemich.telegrambotservice.application.service;

import by.zemich.telegrambotservice.application.service.commands.StateProcessorHandler;
import by.zemich.telegrambotservice.application.service.dialogs.FSM.AdCreationStates;
import by.zemich.telegrambotservice.application.service.dialogs.FSM.AddAdvertisementEvents;
import lombok.RequiredArgsConstructor;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AddAdsStateMachineListenerAdapter extends StateMachineListenerAdapter<AdCreationStates, AddAdvertisementEvents> {

    private final StateProcessorHandler stateProcessorHandler;

    @Override
    public void stateEntered(State<AdCreationStates, AddAdvertisementEvents> state) {
        AdCreationStates currentState = state.getId();
        String chatId = state.getStateActions().getId();
        stateProcessorHandler.getProcessorByState(currentState.name()).proceed(chatId);
    }
}
