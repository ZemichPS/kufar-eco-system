package by.zemich.telegrambotservice.application.service.scenarious.registration.guard;

import by.zemich.telegrambotservice.application.service.scenarious.api.StateMachineContextHelper;
import by.zemich.telegrambotservice.application.service.scenarious.registration.event.UserRegistrationEvent;
import by.zemich.telegrambotservice.application.service.scenarious.registration.state.UserRegistrationState;
import org.springframework.statemachine.StateContext;
import org.springframework.stereotype.Component;

@Component
public class UserNameGuard extends AbstractUserRegistrationGuard {
    @Override
    public boolean evaluate(StateContext<UserRegistrationState, UserRegistrationEvent> context) {
        String username = StateMachineContextHelper.getPreviousStageText(context.getStateMachine());
        return validateUserName(username);
    }

    private boolean validateUserName(String username) {
        return username != null &&
                !username.isBlank() &&
                username.split(" ").length == 2;
    }

    @Override
    public UserRegistrationState getHandleState() {
        return UserRegistrationState.USER_DATA_INPUT;
    }
}
