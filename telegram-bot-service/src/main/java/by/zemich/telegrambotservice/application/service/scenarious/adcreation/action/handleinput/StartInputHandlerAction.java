package by.zemich.telegrambotservice.application.service.scenarious.adcreation.action.handleinput;

import by.zemich.telegrambotservice.application.service.scenarious.adcreation.AdCreationState;
import by.zemich.telegrambotservice.application.service.scenarious.adcreation.AddAdvertisementEvent;
import by.zemich.telegrambotservice.application.service.scenarious.api.StateMachineContextHelper;
import by.zemich.telegrambotservice.application.service.openfeign.UserServiceOpenFeign;
import by.zemich.telegrambotservice.domain.dto.AdvertisementDraftDto;
import lombok.RequiredArgsConstructor;
import org.springframework.statemachine.StateContext;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class StartInputHandlerAction extends AdCreationHandlerInputAction {

    private final UserServiceOpenFeign userServiceOpenFeign;

    @Override
    public void execute(StateContext<AdCreationState, AddAdvertisementEvent> context) {
        Long userId = StateMachineContextHelper.getUserId(context.getStateMachine());
        UUID userUuid = userServiceOpenFeign.getUserIdByTelegramId(userId);
        AdvertisementDraftDto adDraft = new AdvertisementDraftDto(userUuid);
        StateMachineContextHelper.setAdDraft(context.getStateMachine(), adDraft);
    }

    @Override
    public AddAdvertisementEvent handleInput() {
        return AddAdvertisementEvent.STARTED;
    }

    public void validate(String input) {

    }
}
