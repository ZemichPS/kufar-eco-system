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
public class StartRegistrationAction extends AbstractUserRegistrationAction {

    public StartRegistrationAction(TelegramSender<SendMessage> telegramSender) {
        super("", telegramSender);
    }

    @Override
    public void execute(StateContext<UserRegistrationState, UserRegistrationEvent> context) {
        Long chatId = StateMachineContextHelper.getChatId(context.getStateMachine());
        String username = getUserName(context);
        String greeting = String.format("Приветствую Вас, %s! Сейчас я помогу Вам зарегистрироваться в приложении", username);
        SendMessage sendMessage = this.createMessage(chatId, greeting);
        telegramSender.send(sendMessage);
    }

    private String getUserName(StateContext<UserRegistrationState, UserRegistrationEvent> context) {
        Update update = StateMachineContextHelper.getUpdate(context.getStateMachine());
        String firsName = update.getMessage().getFrom().getFirstName();
        String username = update.getMessage().getFrom().getUserName();
        return firsName != null ? firsName : username;
    }

    @Override
    public UserRegistrationState getHandleState() {
        return UserRegistrationState.START;
    }
}
