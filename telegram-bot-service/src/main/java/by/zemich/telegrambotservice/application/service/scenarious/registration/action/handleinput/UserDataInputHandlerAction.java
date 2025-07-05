package by.zemich.telegrambotservice.application.service.scenarious.registration.action.handleinput;

import by.zemich.telegrambotservice.application.service.scenarious.registration.event.UserRegistrationEvent;
import by.zemich.telegrambotservice.application.service.scenarious.registration.state.UserRegistrationState;
import by.zemich.telegrambotservice.domain.dto.UserRegistrationDto;
import org.springframework.statemachine.StateContext;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class UserDataInputHandlerAction extends AbstractUserRegistrationInputHandlerAction {


    @Override
    public void execute(StateContext<UserRegistrationState, UserRegistrationEvent> context) {
        Update update = getUpdate(context);
        UserRegistrationDto userRegistration = this.getUserRegistration(context);
        String firstName = update.getMessage().getFrom().getFirstName();
        String lastName = update.getMessage().getFrom().getLastName();
        String userName = update.getMessage().getFrom().getUserName();

        if (firstName == null || lastName == null) {
            String name = update.getMessage().getText().trim();
            String[] names = name.split(" ");
            firstName = names[0];
            lastName = names[names.length - 1];
        }

        if (userName != null) userRegistration.setUsername(userName);

        userRegistration.setFirstName(firstName);
        userRegistration.setLastName(lastName);
        userRegistration.setChatId(update.getMessage().getChatId());
        userRegistration.setUserId(update.getMessage().getFrom().getId());
        saveUserRegistration(context, userRegistration);
    }
}
