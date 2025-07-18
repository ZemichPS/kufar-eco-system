package by.zemich.telegrambotservice.application.service;

import by.zemich.telegrambotservice.application.service.api.TelegramFileDownloader;
import by.zemich.telegrambotservice.application.service.api.TelegramSender;
import by.zemich.telegrambotservice.application.service.bots.DialogBot;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.File;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class TelegramMessageSender implements TelegramSender<SendMessage>, TelegramFileDownloader {

    private final DialogBot dialogBot;

    public TelegramMessageSender(@Lazy DialogBot dialogBot) {
        this.dialogBot = dialogBot;
    }

    @Override
    public void send(SendMessage sendMessage) {
        try {
            dialogBot.execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public File downloadFile(GetFile getFile) {
        try {
            return dialogBot.execute(getFile);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public String getToken() {
        return "7835063190:AAFFaTsvaeEF0b1jZ0a0VuOt5l5vTYfiLgs";
    }
}
