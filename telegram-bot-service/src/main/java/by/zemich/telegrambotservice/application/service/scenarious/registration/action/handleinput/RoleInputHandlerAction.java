package by.zemich.telegrambotservice.application.service.scenarious.registration.action.handleinput;

import by.zemich.telegrambotservice.application.service.scenarious.registration.event.UserRegistrationEvent;
import by.zemich.telegrambotservice.application.service.scenarious.registration.state.UserRegistrationState;
import by.zemich.telegrambotservice.domain.dto.UserRegistrationDto;
import org.springframework.statemachine.StateContext;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class RoleInputHandlerAction extends AbstractUserRegistrationInputHandlerAction {
    @Override
    public void execute(StateContext<UserRegistrationState, UserRegistrationEvent> context) {
        Update update = getUpdate(context);
        String role = update.getMessage().getText();
        UserRegistrationDto userRegistration = getUserRegistration(context);
        userRegistration.setRole(role);
        saveUserRegistration(context, userRegistration);
    }

    @Override
    public UserRegistrationState getHandleState() {
        return UserRegistrationState.ROLE_INPUT;
    }
}
