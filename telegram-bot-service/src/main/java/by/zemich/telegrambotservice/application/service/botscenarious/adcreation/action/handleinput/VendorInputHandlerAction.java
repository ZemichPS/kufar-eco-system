package by.zemich.telegrambotservice.application.service.botscenarious.adcreation.action.handleinput;


import by.zemich.telegrambotservice.application.service.botscenarious.adcreation.AdCreationState;
import by.zemich.telegrambotservice.application.service.botscenarious.adcreation.AddAdvertisementEvent;
import by.zemich.telegrambotservice.application.service.botscenarious.api.StateMachineContextHelper;
import by.zemich.telegrambotservice.domain.dto.AdvertisementDraftDto;
import org.springframework.statemachine.StateContext;

public class VendorInputHandlerAction extends AdCreationHandlerInputAction {

    @Override
    public void execute(StateContext<AdCreationState, AddAdvertisementEvent> context) {
        String vendorName = StateMachineContextHelper.getPreviousStageText(context.getStateMachine());
        AdvertisementDraftDto adDraft = StateMachineContextHelper.getAdDraft(context.getStateMachine());
        adDraft.setVendorName(vendorName);
        StateMachineContextHelper.setAdDraft(context.getStateMachine(), adDraft);
    }
}
