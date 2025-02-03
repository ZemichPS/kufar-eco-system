package by.zemich.telegrambotservice.application.service.bots;

import by.zemich.telegrambotservice.application.service.api.AbstractTelegramBot;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TelegramBot extends AbstractTelegramBot {

    private final String botUserName;

    public TelegramBot(String botUserName, String token) {
        super(token);
        this.botUserName = botUserName;
    }

    @Override
    public String getBotUsername() {
        return botUserName;
    }
}
