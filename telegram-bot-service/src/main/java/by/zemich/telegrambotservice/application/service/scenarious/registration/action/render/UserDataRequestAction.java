package by.zemich.telegrambotservice.application.service.scenarious.registration.action.render;

import by.zemich.telegrambotservice.application.service.api.TelegramSender;
import by.zemich.telegrambotservice.application.service.scenarious.api.StateMachineContextHelper;
import by.zemich.telegrambotservice.application.service.scenarious.registration.event.UserRegistrationEvent;
import by.zemich.telegrambotservice.application.service.scenarious.registration.state.UserRegistrationState;
import org.springframework.statemachine.StateContext;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class UserDataRequestAction extends AbstractUserRegistrationAction {

    public UserDataRequestAction(TelegramSender<SendMessage> telegramSender) {
        super("", telegramSender);
    }

    @Override
    public void execute(StateContext<UserRegistrationState, UserRegistrationEvent> context) {
        Update update = StateMachineContextHelper.getUpdate(context.getStateMachine());
        String firstName = update.getMessage().getFrom().getFirstName();
        String lastName = update.getMessage().getFrom().getLastName();

        String requestMessage = null;
        String example = "Например: Иван Иванов";
        if (firstName != null && lastName != null)
            requestMessage = String.format("Ваше имя: %s, фамилия: %s? Если это не так напишите имя и фамилию через пробел. %s", firstName, lastName, example);
        if (firstName == null || lastName == null)
            requestMessage = String.format("Напишите ваше имя и фамилию через пробел. %s", example);
        Long chatId = getChatId(context);
        SendMessage sendMessage = createMessage(chatId, requestMessage);
        telegramSender.send(sendMessage);
    }

    @Override
    public UserRegistrationState getType() {
        return UserRegistrationState.USER_DATA_INPUT;
    }
}
