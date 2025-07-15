package by.zemich.telegrambotservice.application.service.scenarious.api;

import by.zemich.telegrambotservice.application.service.scenarious.ScenarioType;
import by.zemich.telegrambotservice.domain.model.UserSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@AllArgsConstructor
public class ScenarioDetector {

    public ScenarioType detectScenario(Update update, UserSession session) {

        Message message = update.getMessage();

        if (message.hasText()) {
            String text = message.getText();
            return text.startsWith("/") ? detectByCommand(text) : detectBySession(session);
        }
        return ScenarioType.DEFAULT;
    }

    private ScenarioType detectBySession(UserSession session) {
        return (session != null && session.getCurrentScenarioType() != null)
                ? session.getCurrentScenarioType()
                : ScenarioType.DEFAULT; // Или переходим в дефолтное состояние
    }

//    private ScenarioType detectBySession(UserSession session) {
//        return (session != null && session.getCurrentScenarioType() != null)
//                ? ScenarioType.CONTINUE_PREVIOUS // Продолжаем текущий сценарий
//                : ScenarioType.DEFAULT; // Или переходим в дефолтное состояние
//    }

    private ScenarioType detectByCommand(String text) {
        return switch (text.split(" ")[0]) {
            case "/add_ad" -> ScenarioType.ADD_ADVERTISEMENT;
            case "/registration" -> ScenarioType.USER_REGISTRATION;
            case "/help" -> ScenarioType.SUPPORT;
            default -> ScenarioType.DEFAULT;
        };
    }
}
