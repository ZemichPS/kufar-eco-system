package by.zemich.telegrambotservice.application.service.botscenarious.api;

import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.transition.Transition;
import org.springframework.statemachine.trigger.Trigger;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component
public class NextEventResolver {

    public <S extends Enum<S>, E extends Enum<E>> Optional<E> resolveNextEvent(StateMachine<S, E> sm) {

        S currentState = sm.getState().getId();

        return sm.getTransitions().stream()
                .filter(t -> t.getSource().getId().equals(currentState))
                .map(Transition::getTrigger)
                .filter(Objects::nonNull)
                .map(Trigger::getEvent)
                .filter(Objects::nonNull)
                .findFirst(); // если несколько, можно выбирать по стратегии
    }
}
