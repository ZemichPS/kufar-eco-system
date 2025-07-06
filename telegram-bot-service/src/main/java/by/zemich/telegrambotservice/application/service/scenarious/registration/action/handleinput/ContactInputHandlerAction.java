package by.zemich.telegrambotservice.application.service.scenarious.registration.action.handleinput;

import by.zemich.telegrambotservice.application.service.scenarious.api.StateMachineContextHelper;
import by.zemich.telegrambotservice.application.service.scenarious.registration.event.UserRegistrationEvent;
import by.zemich.telegrambotservice.application.service.scenarious.registration.state.UserRegistrationState;
import by.zemich.telegrambotservice.domain.dto.UserRegistrationDto;
import org.springframework.statemachine.StateContext;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class ContactInputHandlerAction extends AbstractUserRegistrationInputHandlerAction {
    @Override
    public void execute(StateContext<UserRegistrationState, UserRegistrationEvent> context) {
        Update update = StateMachineContextHelper.getUpdate(context.getStateMachine());
        String phoneNumber = update.getMessage().getContact().getPhoneNumber();
        UserRegistrationDto userRegistration = getUserRegistration(context);
        userRegistration.setPhoneNumber(phoneNumber);
        saveUserRegistration(context, userRegistration);
        sendEvent(context, UserRegistrationEvent.USER_ROLE_INPUTED);
    }
}
