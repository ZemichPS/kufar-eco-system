package by.zemich.telegrambotservice.application.service.bots;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;

@Component
@RequiredArgsConstructor
public class TelegramBotService {

    private final TelegramBotRegistry telegramBotRegistry;

    public void sendPhoto(SendPhoto message) {
        String chatId = message.getChatId();
        TelegramBot bot = telegramBotRegistry.getByChatId(chatId);
        bot.sendPhoto(message);
    }

    public void sendText(SendMessage message) {
        String chatId = message.getChatId();
        TelegramBot bot = telegramBotRegistry.getByChatId(chatId);
        bot.sendText(message);
    }

}
