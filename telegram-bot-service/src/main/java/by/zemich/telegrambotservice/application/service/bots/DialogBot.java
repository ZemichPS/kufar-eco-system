package by.zemich.telegrambotservice.application.service.bots;

import by.zemich.telegrambotservice.application.service.TelegramSender;
import by.zemich.telegrambotservice.application.service.botscenarious.ScenarioType;
import by.zemich.telegrambotservice.application.service.botscenarious.adcreation.AddAdvertisementEvent;
import by.zemich.telegrambotservice.application.service.dialogbotapi.StateMachineOrchestrator;
import by.zemich.telegrambotservice.application.service.dialogbotapi.UserSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import by.zemich.telegrambotservice.application.service.dialogbotapi.ScenarioDetector;


@Component
@RequiredArgsConstructor
public class DialogBot extends TelegramLongPollingBot implements TelegramSender<SendMessage> {

    private final TelegramBotRegistry telegramBotRegistry;
    private final StateMachineOrchestrator stateMachineOrchestrator;
    private final ScenarioDetector scenarioDetector;

    @Override
    public String getBotUsername() {
        return telegramBotRegistry.getByName("dialogBot").getBotUsername();
    }

    @Override
    public void onUpdateReceived(Update update) {

        if (!update.hasMessage() && !update.hasCallbackQuery()) {
            return;
        }
        Long chatId = update.hasMessage()
                ? update.getMessage().getChatId()
                : update.getCallbackQuery().getMessage().getChatId();

        ScenarioType scenarioType = scenarioDetector.detectScenario(update);

        if (update.hasCallbackQuery()) {
            String callbackData = update.getCallbackQuery().getData();
            stateMachineOrchestrator.handleEvent(chatId, scenarioType, callbackData);
        } else stateMachineOrchestrator.handleEvent(chatId, scenarioType);
    }

    @Override
    public void send(SendMessage sendMessage) {
        try {
            this.execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
