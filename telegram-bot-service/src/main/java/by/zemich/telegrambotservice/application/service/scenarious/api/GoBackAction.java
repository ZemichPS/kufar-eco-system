package by.zemich.telegrambotservice.application.service.scenarious.api;

import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.action.Action;

public interface GoBackAction<S, E> extends Action<S, E> {

    default void goBack(StateContext<S, E> context) {
        StateMachine<S, E> stateMachine = context.getStateMachine();
        E previousEvent = stateMachine.getExtendedState().get("previousEvent", (Class<E>) context.getEvent().getClass());
        stateMachine.sendEvent(previousEvent);
    }

}
