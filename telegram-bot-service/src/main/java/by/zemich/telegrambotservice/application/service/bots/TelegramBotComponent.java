package by.zemich.telegrambotservice.application.service.bots;

import by.zemich.telegrambotservice.application.service.TelegramDialogueCoordinator;
import by.zemich.telegrambotservice.application.service.TelegramSender;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


@Component
public class TelegramBotComponent extends TelegramLongPollingBot implements TelegramSender<SendMessage> {

    private final TelegramBotRegistry telegramBotRegistry;
    private final TelegramDialogueCoordinator dialogueCoordinator;

    public TelegramBotComponent(TelegramBotRegistry telegramBotRegistry,
                                TelegramDialogueCoordinator dialogueCoordinator) {
        super(telegramBotRegistry.getByName("dialogBot").getBotToken());
        this.telegramBotRegistry = telegramBotRegistry;
        this.dialogueCoordinator = dialogueCoordinator;
    }

    @Override
    public String getBotUsername() {
        return telegramBotRegistry.getByName("dialogBot").getBotUsername();
    }

    @Override
    public void onUpdateReceived(Update update) {
        dialogueCoordinator.handle(update);
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
