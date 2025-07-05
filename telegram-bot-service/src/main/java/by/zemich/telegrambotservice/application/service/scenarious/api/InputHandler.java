package by.zemich.telegrambotservice.application.service.scenarious.api;

import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;


public interface InputHandler<S, E> extends Action<S, E> {

    void handleInput(StateContext<S, E> context);
}
