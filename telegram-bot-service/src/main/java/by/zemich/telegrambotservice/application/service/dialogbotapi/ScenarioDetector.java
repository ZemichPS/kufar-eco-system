package by.zemich.telegrambotservice.application.service.dialogbotapi;

import by.zemich.telegrambotservice.application.service.botscenarious.ScenarioType;
import by.zemich.telegrambotservice.domain.model.UserSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@AllArgsConstructor
public class ScenarioDetector {
    private final UserSessionService userSessionService;

    public ScenarioType detectScenario(Update update) {
        if (!update.hasMessage()) {
            return ScenarioType.DEFAULT;
        }

        Long chatId = update.getMessage().getChatId();
        // TODO заменить на orElse()
        UserSession session = userSessionService.findByChatId(chatId).get();

        if (session != null && session.getCurrentScenarioType() != null) {
            return session.getCurrentScenarioType();
        }

        Message message = update.getMessage();

        if (message.hasPhoto()) return ScenarioType.PHOTO_PROCESSING;

        if (message.hasText()) {
            String text = message.getText();
            return text.startsWith("/") ? detectByCommand(text) : detectBySession(session);
        }
        return ScenarioType.DEFAULT;
    }

    private ScenarioType detectBySession(UserSession session) {
        return (session != null && session.getCurrentScenarioType() != null)
                ? ScenarioType.CONTINUE_PREVIOUS // Продолжаем текущий сценарий
                : ScenarioType.DEFAULT; // Или переходим в дефолтное состояние
    }

    private ScenarioType detectByCommand(String text) {
        return switch (text.split(" ")[0]) {
            case "/add_ad" -> ScenarioType.ADD_ADVERTISEMENT;
            case "/registration" -> ScenarioType.USER_REGISTRATION;
            case "/help" -> ScenarioType.SUPPORT;
            default -> ScenarioType.DEFAULT;
        };
    }
}
