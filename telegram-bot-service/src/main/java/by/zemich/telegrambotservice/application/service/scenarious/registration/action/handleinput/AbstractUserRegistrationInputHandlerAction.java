package by.zemich.telegrambotservice.application.service.scenarious.registration.action.handleinput;

import by.zemich.telegrambotservice.application.service.scenarious.api.StateHandleable;
import by.zemich.telegrambotservice.application.service.scenarious.registration.event.UserRegistrationEvent;
import by.zemich.telegrambotservice.application.service.scenarious.registration.state.UserRegistrationState;
import by.zemich.telegrambotservice.domain.dto.UserRegistrationDto;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.telegram.telegrambots.meta.api.objects.Update;

public abstract class AbstractUserRegistrationInputHandlerAction implements Action<UserRegistrationState, UserRegistrationEvent>, StateHandleable<UserRegistrationState> {

    protected UserRegistrationDto getUserRegistration(StateContext<UserRegistrationState, UserRegistrationEvent> context) {
        return context.getExtendedState().get("userRegistration", UserRegistrationDto.class);
    }

    protected void saveUserRegistration(StateContext<UserRegistrationState, UserRegistrationEvent> context,
                                        UserRegistrationDto userRegistrationDto) {
        context.getExtendedState().getVariables().put("userRegistrationDto", userRegistrationDto);
    }

    protected Update getUpdate(StateContext<UserRegistrationState, UserRegistrationEvent> context) {
        return context.getExtendedState().get("userRegistration", Update.class);
    }

    protected void sendEvent(StateContext<UserRegistrationState, UserRegistrationEvent> context, UserRegistrationEvent event){
        context.getStateMachine().sendEvent(event);
    }
}