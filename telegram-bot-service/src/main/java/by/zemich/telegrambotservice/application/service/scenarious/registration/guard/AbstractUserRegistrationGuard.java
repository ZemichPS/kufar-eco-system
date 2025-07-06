package by.zemich.telegrambotservice.application.service.scenarious.registration.guard;

import by.zemich.telegrambotservice.application.service.scenarious.api.CustomGuard;
import by.zemich.telegrambotservice.application.service.scenarious.registration.event.UserRegistrationEvent;
import by.zemich.telegrambotservice.application.service.scenarious.registration.state.UserRegistrationState;

public abstract class AbstractUserRegistrationGuard implements CustomGuard<UserRegistrationState, UserRegistrationEvent> {
}
