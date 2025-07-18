package by.zemich.telegrambotservice.application.service.bots;

import by.zemich.telegrambotservice.application.service.api.TelegramFileDownloader;
import by.zemich.telegrambotservice.application.service.api.TelegramSender;
import by.zemich.telegrambotservice.application.service.scenarious.api.UpdateHandler;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.File;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


@Component
@Slf4j
public class DialogBot extends TelegramLongPollingBot {

    private final UpdateHandler updateHandler;

    public DialogBot(UpdateHandler updateHandler) {
        super("7835063190:AAFFaTsvaeEF0b1jZ0a0VuOt5l5vTYfiLgs");
        this.updateHandler = updateHandler;
    }

    @Override
    public String getBotUsername() {
        return "DialogueBot";
    }

    @Override
    public void onUpdateReceived(Update update) {
        updateHandler.handle(update);
    }

}
