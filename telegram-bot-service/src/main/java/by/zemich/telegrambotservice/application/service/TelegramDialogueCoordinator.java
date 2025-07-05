package by.zemich.telegrambotservice.application.service;

import by.zemich.telegrambotservice.application.service.scenarious.ScenarioType;
import lombok.RequiredArgsConstructor;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.springframework.session.Session;

import java.util.Map;


@Service
@RequiredArgsConstructor
public class TelegramDialogueCoordinator {

    private final StateMachineHandler stateMachineHandler;
    private final FindByIndexNameSessionRepository<? extends Session> sessions;

    public void handle(Update update) {
        Long chatId = update.getMessage().getChat().getId();
        ScenarioType scenarioType = getCurrentDialogTypeForChatId(chatId);
        StateMachineFactory<?, ?> stateMachineFactory = stateMachineHandler.getByDialogType(scenarioType);
        StateMachine<?, ?> stateMachine = stateMachineFactory.getStateMachine(String.valueOf(chatId));
        String text = update.getMessage().getText();
    }

    private ScenarioType getCurrentDialogTypeForChatId(Long chatId){
        Map<String, ? extends Session> sessionsMap = sessions.findByIndexNameAndIndexValue(FindByIndexNameSessionRepository.PRINCIPAL_NAME_INDEX_NAME, chatId.toString());
        if (sessionsMap.isEmpty()) {
            return ScenarioType.DEFAULT;
        }
        Session session = sessionsMap.values().iterator().next();
        return session.getAttribute("dialog_type");
    }

    private DialogEvent extractEventFromUpdate(Update update) {
        // В real-world анализируется текст, callback и др.
        // В учебном примеру просто по тексту
        String text = update.message().text();

        if (text.startsWith("/start_registration")) return DialogEvent.REGISTRATION_STARTED;
        if (text.startsWith("/addProduct")) return DialogEvent.PRODUCT_ADD_STARTED;
        if (text.startsWith("/searchProduct")) return DialogEvent.PRODUCT_SEARCH_STARTED;

        return DialogEvent.UNKNOWN;
    }
}
