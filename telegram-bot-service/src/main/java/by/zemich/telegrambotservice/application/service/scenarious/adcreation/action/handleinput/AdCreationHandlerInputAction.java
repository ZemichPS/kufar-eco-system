package by.zemich.telegrambotservice.application.service.scenarious.adcreation.action.handleinput;

import by.zemich.telegrambotservice.application.service.scenarious.adcreation.AdCreationState;
import by.zemich.telegrambotservice.application.service.scenarious.adcreation.AddAdvertisementEvent;
import by.zemich.telegrambotservice.application.service.scenarious.api.BaseHandlerInputAction;
import by.zemich.telegrambotservice.application.service.scenarious.api.StateMachineContextHelper;
import by.zemich.telegrambotservice.domain.dto.AdvertisementDraftDto;
import org.springframework.statemachine.StateContext;

public abstract class AdCreationHandlerInputAction implements BaseHandlerInputAction<AdCreationState, AddAdvertisementEvent> {

    protected AdvertisementDraftDto getAdDraft (StateContext<AdCreationState, AddAdvertisementEvent> context){
        return StateMachineContextHelper.getAdDraft(context.getStateMachine());
    }

}
