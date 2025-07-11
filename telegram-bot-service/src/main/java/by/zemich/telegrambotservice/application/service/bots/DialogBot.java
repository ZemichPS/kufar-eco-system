package by.zemich.telegrambotservice.application.service.bots;

import by.zemich.telegrambotservice.application.service.api.TelegramFileDownloader;
import by.zemich.telegrambotservice.application.service.api.TelegramSender;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.File;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import by.zemich.telegrambotservice.application.service.scenarious.api.UpdateHandler;


@Component
@RequiredArgsConstructor
@Slf4j
public class DialogBot extends TelegramLongPollingBot implements TelegramSender<SendMessage>, TelegramFileDownloader {

    private final UpdateHandler updateHandler;

    @PostConstruct
    private void init(){
        log.info("Initializing Bot name: {}", getBotUsername());
    }

    @Override
    public String getBotUsername() {
        return "DialogueBot";
    }

    @Override
    public void onUpdateReceived(Update update) {
        log.info("Update received: {}", update.getMessage().getText());
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
        return "7835063190:AAFFaTsvaeEF0b1jZ0a0VuOt5l5vTYfiLgs";
    }
}
