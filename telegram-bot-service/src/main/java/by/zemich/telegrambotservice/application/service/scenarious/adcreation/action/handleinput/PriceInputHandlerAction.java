package by.zemich.telegrambotservice.application.service.scenarious.adcreation.action.handleinput;


import by.zemich.telegrambotservice.application.service.scenarious.adcreation.AdCreationState;
import by.zemich.telegrambotservice.application.service.scenarious.adcreation.AddAdvertisementEvent;
import by.zemich.telegrambotservice.application.service.scenarious.api.StateMachineContextHelper;
import by.zemich.telegrambotservice.domain.dto.AdvertisementDraftDto;
import org.springframework.statemachine.StateContext;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class PriceInputHandlerAction extends AdCreationHandlerInputAction {

    @Override
    public void execute(StateContext<AdCreationState, AddAdvertisementEvent> context) {
        String price = StateMachineContextHelper.getPreviousStageText(context.getStateMachine());
        validate(price);
        AdvertisementDraftDto adDraft = this.getAdDraft(context);
        adDraft.setPrice(price);
        StateMachineContextHelper.setAdDraft(context.getStateMachine(), adDraft);
    }

    @Override
    public AddAdvertisementEvent handleInput() {
        return AddAdvertisementEvent.MODEL_RECEIVED;
    }

    public void validate(String input) {
        try {
            BigDecimal value = new BigDecimal(input);
            if (value.compareTo(BigDecimal.ZERO) > 0
                    && value.scale() <= 2) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Неверная модель");
        }
    }
}
