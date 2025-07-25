package by.zemich.telegrambotservice.application.service.scenarious.registration.guard;

import by.zemich.telegrambotservice.application.service.openfeign.UserServiceOpenFeign;
import by.zemich.telegrambotservice.application.service.scenarious.api.StateMachineContextHelper;
import by.zemich.telegrambotservice.application.service.scenarious.registration.event.UserRegistrationEvent;
import by.zemich.telegrambotservice.application.service.scenarious.registration.state.UserRegistrationState;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.statemachine.StateContext;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
@Log4j2
public class UserExistsGuard extends AbstractUserRegistrationGuard {

    private final UserServiceOpenFeign userServiceOpenFeign;

    @Override
    public boolean evaluate(StateContext<UserRegistrationState, UserRegistrationEvent> context) {
        Update update = StateMachineContextHelper.getUpdate(context.getStateMachine());
        Long userId = update.getMessage().getFrom().getId();
        log.info("сейчас мы проверим пользователя. Работает guard {}", this.getClass().getSimpleName());
        return !userServiceOpenFeign.existsByTelegramId(userId);
    }

    @Override
    public UserRegistrationState getHandleState() {
        return UserRegistrationState.USER_EXISTS;
    }
}
