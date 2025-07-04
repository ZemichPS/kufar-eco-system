package by.zemich.telegrambotservice.application.service.botscenarious.api;

import org.springframework.statemachine.StateContext;

public abstract class BaseInputHandlerAction<S, E> implements InputHandler<S, E> {

    protected <T> T getOutput(StateContext<S, E> context, String key, Class<T> tClass) {
        return context.getExtendedState().get(key, tClass);
    }
}
