package by.zemich.telegrambotservice.application.service.scenarious.registration.guard;

import by.zemich.telegrambotservice.application.service.openfeign.UserServiceOpenFeign;
import by.zemich.telegrambotservice.application.service.scenarious.api.StateMachineContextHelper;
import by.zemich.telegrambotservice.application.service.scenarious.registration.event.UserRegistrationEvent;
import by.zemich.telegrambotservice.application.service.scenarious.registration.state.UserRegistrationState;
import lombok.RequiredArgsConstructor;
import org.springframework.statemachine.StateContext;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class UserExistsGuard extends AbstractUserRegistrationGuard {

    private final UserServiceOpenFeign userServiceOpenFeign;

    @Override
    public boolean evaluate(StateContext<UserRegistrationState, UserRegistrationEvent> context) {
        Update update = StateMachineContextHelper.getUpdate(context.getStateMachine());
        Long userId = update.getMessage().getFrom().getId();
        return !userServiceOpenFeign.existsByTelegramId(userId);
    }

    @Override
    public UserRegistrationState getHandleState() {
        return UserRegistrationState.USER_DATA_INPUT;
    }
}
