package by.zemich.telegrambotservice.application.service.scenarious.registration.action.render;

import by.zemich.telegrambotservice.application.service.api.TelegramSender;
import by.zemich.telegrambotservice.application.service.scenarious.api.KeyboardUtil;
import by.zemich.telegrambotservice.application.service.scenarious.api.RoleProvider;
import by.zemich.telegrambotservice.application.service.scenarious.registration.event.UserRegistrationEvent;
import by.zemich.telegrambotservice.application.service.scenarious.registration.state.UserRegistrationState;
import org.springframework.statemachine.StateContext;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

import java.util.List;

@Component
public class RoleChoiceAction extends AbstractUserRegistrationAction implements RoleProvider {

    public RoleChoiceAction(TelegramSender<SendMessage> telegramSender) {
        super("Кто Вы? \uD83E\uDDD0", telegramSender);
    }

    @Override
    public void execute(StateContext<UserRegistrationState, UserRegistrationEvent> context) {
        InlineKeyboardMarkup keyboardMarkup = KeyboardUtil.createInlineKeyboardMarkup(getRoles("lists/roles"), 1);
        SendMessage sendMessage = createMessage(getChatId(context), ACTION_TEXT, keyboardMarkup);
        telegramSender.send(sendMessage);
    }

    @Override
    public UserRegistrationState getHandleState() {
        return UserRegistrationState.ROLE_INPUT;
    }

}
