package by.zemich.telegrambotservice.application.service.scenarious.api;

import org.springframework.statemachine.guard.Guard;

public interface CustomGuard<S extends Enum<S>, E> extends Guard<S, E>, StateHandleable<S> {
}
