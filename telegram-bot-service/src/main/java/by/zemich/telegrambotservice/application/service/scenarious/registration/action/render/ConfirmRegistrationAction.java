package by.zemich.telegrambotservice.application.service.scenarious.registration.action.render;

import by.zemich.telegrambotservice.application.service.api.TelegramSender;
import by.zemich.telegrambotservice.application.service.openfeign.UserServiceOpenFeign;
import by.zemich.telegrambotservice.application.service.scenarious.api.KeyboardUtil;
import by.zemich.telegrambotservice.application.service.scenarious.api.RoleProvider;
import by.zemich.telegrambotservice.application.service.scenarious.registration.event.UserRegistrationEvent;
import by.zemich.telegrambotservice.application.service.scenarious.registration.state.UserRegistrationState;
import by.zemich.telegrambotservice.domain.dto.UserRegistrationDto;
import org.springframework.statemachine.StateContext;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

@Component

public class ConfirmRegistrationAction extends AbstractUserRegistrationAction {

    private final UserServiceOpenFeign userServiceOpenFeign;

    public ConfirmRegistrationAction(TelegramSender<SendMessage> telegramSender, UserServiceOpenFeign userServiceOpenFeign) {
        super("Поздравляем с успешной регистрацией!", telegramSender);
        this.userServiceOpenFeign = userServiceOpenFeign;
    }

    @Override
    public void execute(StateContext<UserRegistrationState, UserRegistrationEvent> context) {
        UserRegistrationDto userRegistrationDto = getUserRegistration(context);
        String text = String.format(ACTION_TEXT + " Ваш профиль в системе:\n %s", userRegistrationDto);
        userServiceOpenFeign.saveUser(userRegistrationDto);
        SendMessage sendMessage = createMessage(getChatId(context), ACTION_TEXT);
        telegramSender.send(sendMessage);
    }

    @Override
    public UserRegistrationState getHandleState() {
        return UserRegistrationState.CONFIRM_REGISTRATION;
    }
}
