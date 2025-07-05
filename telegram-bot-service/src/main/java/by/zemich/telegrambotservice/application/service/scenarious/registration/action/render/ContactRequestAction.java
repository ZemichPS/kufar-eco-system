package by.zemich.telegrambotservice.application.service.scenarious.registration.action.render;

import by.zemich.telegrambotservice.application.service.api.TelegramSender;
import by.zemich.telegrambotservice.application.service.scenarious.registration.event.UserRegistrationEvent;
import by.zemich.telegrambotservice.application.service.scenarious.registration.state.UserRegistrationState;
import org.springframework.statemachine.StateContext;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.List;

@Component
public class ContactRequestAction extends AbstractUserRegistrationAction {

    public ContactRequestAction(TelegramSender<SendMessage> telegramSender) {
        super("Поделитесь номером для регистрации:", telegramSender);
    }

    @Override
    public void execute(StateContext<UserRegistrationState, UserRegistrationEvent> context) {
        Long chatId = getChatId(context);
        ReplyKeyboardMarkup keyboard = new ReplyKeyboardMarkup();
        KeyboardButton requestPhoneButton = new KeyboardButton("Отправить номер телефона");
        requestPhoneButton.setRequestContact(true);
        keyboard.setKeyboard(List.of(new KeyboardRow(List.of(requestPhoneButton))));
        SendMessage message = createMessage(chatId, ACTION_TEXT, keyboard);
        telegramSender.send(message);
    }

    @Override
    public UserRegistrationState getHandleState() {
        return UserRegistrationState.CONTACT_INPUT;
    }
}
