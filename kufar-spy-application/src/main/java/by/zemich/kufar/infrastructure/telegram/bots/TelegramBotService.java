package by.zemich.kufar.infrastructure.telegram.bots;

import by.zemich.kufar.application.service.api.PhotoMessenger;
import by.zemich.kufar.application.service.api.TextMessenger;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;

@Component
@RequiredArgsConstructor
public class TelegramBotService implements TextMessenger<SendMessage>, PhotoMessenger<SendPhoto> {

    private final TelegramBotRegistry telegramBotRegistry;

    @Override
    public void sendPhoto(SendPhoto message) {
        String chatId = message.getChatId();
        TelegramBot bot = telegramBotRegistry.getByChatId(chatId);
        bot.sendPhoto(message);
    }

    @Override
    public void sendText(SendMessage message) {
        String chatId = message.getChatId();
        TelegramBot bot = telegramBotRegistry.getByChatId(chatId);
        bot.sendText(message);
    }

}
