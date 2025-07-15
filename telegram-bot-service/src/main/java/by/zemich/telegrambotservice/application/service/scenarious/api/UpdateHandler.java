package by.zemich.telegrambotservice.application.service.scenarious.api;

import by.zemich.telegrambotservice.application.service.scenarious.ScenarioType;
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

        UserSession session = userSessionService.findByChatId(chatId)
                .orElseGet(() -> userSessionService.create(chatId));

        ScenarioType scenarioType = scenarioDetector.detectScenario(update, session);
        if (session.getCurrentScenarioType() == null) session.setCurrentScenarioType(scenarioType);
        StateMachine<S, E> stateMachine = stateMachineOrchestrator.getStateMachineBySession(session);
        userSessionService.update(session);
        stateMachine.start();
    }

    private Long getChatId(Update update) {
        return update.hasMessage()
                ? update.getMessage().getChatId()
                : update.getCallbackQuery().getMessage().getChatId();
    }

    private String getMessageText(Update update) {
        return update.hasMessage()
                ? update.getMessage().getText()
                : update.getCallbackQuery().getData();
    }

    Long getUserId(Update update) {
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


}
