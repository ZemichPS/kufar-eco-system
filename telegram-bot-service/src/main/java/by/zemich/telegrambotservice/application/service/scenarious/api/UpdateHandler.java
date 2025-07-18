package by.zemich.telegrambotservice.application.service.scenarious.api;

import by.zemich.telegrambotservice.application.service.scenarious.ScenarioType;
import by.zemich.telegrambotservice.application.service.scenarious.registration.event.UserRegistrationEvent;
import by.zemich.telegrambotservice.domain.model.UserSession;
import lombok.RequiredArgsConstructor;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class UpdateHandler {

    private final StateMachineOrchestrator stateMachineOrchestrator;
    private final ScenarioDetector scenarioDetector;
    private final UserSessionService userSessionService;
    //private final NextEventResolver nextEventResolver;

    public <E extends Enum<E>, S extends Enum<S>> void handle(Update update) {

        Long chatId = getChatId(update);
        Long userId = getUserId(update);

        UserSession session = userSessionService.findByUserId(userId)
                .orElseGet(() -> userSessionService.create(chatId, userId));

        if (session.getCurrentScenarioType() == null) {
            ScenarioType scenarioType = scenarioDetector.detectScenario(update, session);
            session.setCurrentScenarioType(scenarioType);
        }

        StateMachine<S, E> stateMachine = stateMachineOrchestrator.getStateMachineBySession(session);
        populateStateContext(stateMachine, update);
        userSessionService.update(session);
        stateMachine.start();

        if(update.hasCallbackQuery()) {
            String callback = update.getCallbackQuery().getData();
            if (callback.startsWith("event:")) {
                String event = extractEvent(callback);
                E e = (E) Enum.valueOf(UserRegistrationEvent.class, event);
                stateMachine.sendEvent(e);
            }
        }
    }

    private <E extends Enum<E>, S extends Enum<S>> void populateStateContext(
            StateMachine<S, E> stateMachine,
            Update update
    ){
        stateMachine.getExtendedState().getVariables().put("update", update);
        stateMachine.getExtendedState().getVariables().put("chatId", getChatId(update));
    }

    private Long getChatId(Update update) {
        return update.hasMessage()
                ? update.getMessage().getChatId()
                : update.getCallbackQuery().getMessage().getChatId();
    }



    private Long getUserId(Update update) {
        Long userId = null;
        if (update.hasMessage()) {
            userId = update.getMessage().getFrom().getId();
        } else if (update.hasCallbackQuery()) {
            userId = update.getCallbackQuery().getFrom().getId();
        } else if (update.hasInlineQuery()) {
            userId = update.getInlineQuery().getFrom().getId();
        } else if (update.hasChosenInlineQuery()) {
            userId = update.getChosenInlineQuery().getFrom().getId();
        } else if (update.hasMyChatMember()) {
            userId = update.getMyChatMember().getFrom().getId();
        }
        return userId;
    }

    private String  extractEvent(String callBackData){
        return callBackData.toUpperCase().substring("event:".length());
    }


}
