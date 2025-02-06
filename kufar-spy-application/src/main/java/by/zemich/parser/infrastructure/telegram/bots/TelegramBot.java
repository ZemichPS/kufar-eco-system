package by.zemich.parser.infrastructure.telegram.bots;

import by.zemich.parser.infrastructure.telegram.api.AbstractTelegramBot;
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
