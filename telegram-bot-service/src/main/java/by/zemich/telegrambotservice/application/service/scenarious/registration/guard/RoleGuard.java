package by.zemich.telegrambotservice.application.service.scenarious.registration.guard;

import by.zemich.telegrambotservice.application.service.scenarious.api.RoleProvider;
import by.zemich.telegrambotservice.application.service.scenarious.api.StateMachineContextHelper;
import by.zemich.telegrambotservice.application.service.scenarious.registration.event.UserRegistrationEvent;
import by.zemich.telegrambotservice.application.service.scenarious.registration.state.UserRegistrationState;
import org.springframework.statemachine.StateContext;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class RoleGuard extends AbstractUserRegistrationGuard implements RoleProvider {
    @Override
    public boolean evaluate(StateContext<UserRegistrationState, UserRegistrationEvent> context) {
        Update update = StateMachineContextHelper.getUpdate(context.getStateMachine());
        String role = update.getMessage().getText();
        return validate(role);
    }

    private boolean validate(String role) {
        return getRoles("lists/roles").contains(role);
    }


}
