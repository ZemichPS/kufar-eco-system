package by.zemich.telegrambotservice.application.service.scenarious.registration.action.render;

import by.zemich.telegrambotservice.application.service.api.TelegramSender;
import by.zemich.telegrambotservice.application.service.scenarious.api.KeyboardUtil;
import by.zemich.telegrambotservice.application.service.scenarious.api.StateMachineContextHelper;
import by.zemich.telegrambotservice.application.service.scenarious.registration.event.UserRegistrationEvent;
import by.zemich.telegrambotservice.application.service.scenarious.registration.state.UserRegistrationState;
import org.springframework.statemachine.StateContext;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.List;

@Component
public class StartRegistrationAction extends AbstractUserRegistrationAction {

    public StartRegistrationAction(TelegramSender<SendMessage> telegramSender) {
        super("", telegramSender);
    }

    @Override
    public void execute(StateContext<UserRegistrationState, UserRegistrationEvent> context) {
        Long chatId = StateMachineContextHelper.getChatId(context);
        String username = getUserName(context);
        String greeting = String.format("Приветствую Вас, %s! Сейчас я помогу Вам зарегистрироваться в приложении.", username);
        InlineKeyboardMarkup contonueInlineKeyboardMarkup = KeyboardUtil.createInlineKeyboardMarkup(List.of("Далее..."), List.of("event:CONTINUE_REGISTRATION"));
        SendMessage sendMessage = this.createMessage(chatId, greeting, contonueInlineKeyboardMarkup);
        telegramSender.send(sendMessage);
    }

    private String getUserName(StateContext<UserRegistrationState, UserRegistrationEvent> context) {
        String firsName = context.getExtendedState().get("username", String.class);
        String username = context.getExtendedState().get("firsName", String.class);
        return firsName != null ? firsName : username;
    }


    @Override
    public UserRegistrationState getHandleState() {
        return UserRegistrationState.START_REGISTRATION;
    }

}
