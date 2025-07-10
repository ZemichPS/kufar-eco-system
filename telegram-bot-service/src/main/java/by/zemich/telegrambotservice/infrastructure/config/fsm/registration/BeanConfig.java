package by.zemich.telegrambotservice.infrastructure.config.fsm.registration;

import by.zemich.telegrambotservice.application.service.scenarious.api.BaseRenderAction;
import by.zemich.telegrambotservice.application.service.scenarious.api.CustomGuard;
import by.zemich.telegrambotservice.application.service.scenarious.registration.action.handleinput.AbstractUserRegistrationInputHandlerAction;
import by.zemich.telegrambotservice.application.service.scenarious.registration.event.UserRegistrationEvent;
import by.zemich.telegrambotservice.application.service.scenarious.registration.state.UserRegistrationState;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
public class BeanConfig {

    @Bean
    public Map<UserRegistrationState, CustomGuard<UserRegistrationState, UserRegistrationEvent>> registrationGuardMap(
            List<CustomGuard<UserRegistrationState, UserRegistrationEvent>> guardsList
    ) {
        return guardsList.stream()
                .collect(Collectors.toMap(CustomGuard::getHandleState, guard -> guard));
    }

    @Bean
    public Map<UserRegistrationState, AbstractUserRegistrationInputHandlerAction> registrationInputhandlerMap(
            List<AbstractUserRegistrationInputHandlerAction> inputHandlerList
    ) {
        return inputHandlerList.stream()
                .collect(Collectors.toMap(AbstractUserRegistrationInputHandlerAction::getHandleState, inputHandler -> inputHandler));
    }

    @Bean
    public Map<UserRegistrationState, BaseRenderAction<UserRegistrationState, UserRegistrationEvent>> registrationRenderActionMap(
            List<BaseRenderAction<UserRegistrationState, UserRegistrationEvent>> actionList
    ) {
        return actionList.stream()
                .collect(Collectors.toMap(BaseRenderAction::getHandleState,
                        action -> action));
    }


}
