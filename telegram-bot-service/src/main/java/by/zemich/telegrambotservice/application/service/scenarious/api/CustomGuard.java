package by.zemich.telegrambotservice.application.service.scenarious.api;

import by.zemich.telegrambotservice.application.service.scenarious.registration.state.UserRegistrationState;
import org.springframework.statemachine.guard.Guard;

public interface CustomGuard<S extends Enum<S>, E> extends Guard<S, E>, TypeHandler<S> {
}
