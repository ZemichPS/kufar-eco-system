package by.zemich.telegrambotservice.application.service.scenarious.adcreation.action.handleinput;


import by.zemich.telegrambotservice.application.service.scenarious.adcreation.AdCreationState;
import by.zemich.telegrambotservice.application.service.scenarious.adcreation.AddAdvertisementEvent;
import by.zemich.telegrambotservice.application.service.scenarious.api.StateMachineContextHelper;
import by.zemich.telegrambotservice.domain.dto.AdvertisementDraftDto;
import org.springframework.statemachine.StateContext;
import org.springframework.stereotype.Component;

@Component
public class ManufacturerInputHandlerAction extends AdCreationHandlerInputAction {

    @Override
    public void execute(StateContext<AdCreationState, AddAdvertisementEvent> context) {
        String vendorName = StateMachineContextHelper.getPreviousStageText(context.getStateMachine());
        validate(vendorName);
        AdvertisementDraftDto adDraft = this.getAdDraft(context);
        adDraft.setVendorName(vendorName);
        StateMachineContextHelper.setAdDraft(context.getStateMachine(), adDraft);
    }

    @Override
    public AddAdvertisementEvent handleInput() {
        return AddAdvertisementEvent.MANUFACTURER_RECEIVED;
    }

    @Override
    public void validate(String input) {
        if (input.isEmpty()) throw new IllegalArgumentException("Неверный производитель");
    }
}
