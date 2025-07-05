package by.zemich.telegrambotservice.application.service.scenarious.registration.guard;

import by.zemich.telegrambotservice.application.service.scenarious.registration.event.UserRegistrationEvent;
import by.zemich.telegrambotservice.application.service.scenarious.registration.state.UserRegistrationState;
import org.springframework.statemachine.guard.Guard;

public abstract class AbstractUserRegistrationGuard implements Guard<UserRegistrationState, UserRegistrationEvent> {
}
