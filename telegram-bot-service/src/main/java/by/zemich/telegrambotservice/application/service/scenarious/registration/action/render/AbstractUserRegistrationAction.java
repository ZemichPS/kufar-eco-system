package by.zemich.telegrambotservice.application.service.scenarious.registration.action.render;

import by.zemich.telegrambotservice.application.service.api.TelegramSender;
import by.zemich.telegrambotservice.application.service.scenarious.api.BaseRenderAction;
import by.zemich.telegrambotservice.application.service.scenarious.registration.event.UserRegistrationEvent;
import by.zemich.telegrambotservice.application.service.scenarious.registration.state.UserRegistrationState;
import by.zemich.telegrambotservice.domain.dto.UserRegistrationDto;
import org.springframework.statemachine.StateContext;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public abstract class AbstractUserRegistrationAction extends BaseRenderAction<UserRegistrationState, UserRegistrationEvent> {

    public AbstractUserRegistrationAction(String ACTION_TEXT, TelegramSender<SendMessage> telegramSender) {
        super(ACTION_TEXT, telegramSender);
    }

    protected UserRegistrationDto getUserRegistration(StateContext<UserRegistrationState, UserRegistrationEvent> context){
        return context.getExtendedState().get("userRegistration", UserRegistrationDto.class);
    }

}
