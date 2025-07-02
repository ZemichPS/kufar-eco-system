package by.zemich.telegrambotservice.application.service.bots;

import by.zemich.telegrambotservice.application.service.api.TelegramFileDownloader;
import by.zemich.telegrambotservice.application.service.api.TelegramSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.File;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import by.zemich.telegrambotservice.application.service.botscenarious.api.UpdateHandler;


@Component
@RequiredArgsConstructor
public class DialogBot extends TelegramLongPollingBot implements TelegramSender<SendMessage>, TelegramFileDownloader {

    private final TelegramBotRegistry telegramBotRegistry;
    private final UpdateHandler updateHandler;


    @Override
    public String getBotUsername() {
        return telegramBotRegistry.getByName("dialogBot").getBotUsername();
    }

    @Override
    public void onUpdateReceived(Update update) {
        updateHandler.handle(update);
    }

    @Override
    public void send(SendMessage sendMessage) {
        try {
            this.execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public File downloadFile(GetFile getFile) {
        try {
            return execute(getFile);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getToken() {
        return telegramBotRegistry.getByName("dialogBot").getBotToken();
    }
}
