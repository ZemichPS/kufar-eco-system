package by.zemich.telegrambotservice.application.service.scenarious.api;

import org.springframework.statemachine.action.Action;

public interface BaseHandlerInputAction<S, E> extends Action<S, E> {

    E handleInput();

}
