package by.zemich.telegrambotservice.application.service.bots;

import by.zemich.telegrambotservice.infrastructure.properties.ChannelsBotProperties;
import by.zemich.telegrambotservice.infrastructure.properties.TelegramProperties;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

@Component
@RequiredArgsConstructor
public class TelegramBotRegistry {

    private final ChannelsBotProperties channelsBotProperties;
    private final TelegramProperties telegramProperties;
    private final List<TelegramBot> telegramBotList = new ArrayList<>();
    private final Map<String, TelegramBot> chatIdToBotCache = new ConcurrentHashMap<>();
    private final ReentrantLock lock = new ReentrantLock();

    @PostConstruct
    private void initBots() {
        telegramProperties
                .getBots()
                .forEach((name, token) -> telegramBotList.add(new TelegramBot(name, token)));
    }

    public TelegramBot getByChatId(String chatId) {
        return chatIdToBotCache.computeIfAbsent(chatId, this::findBotByChatId);
    }

    public TelegramBot getByName(String name) {
        return telegramBotList.stream().filter(telegramBot -> telegramBot.getBotUsername().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow();
    }


    private TelegramBot findBotByChatId(String chatId) {
        lock.lock();
        try {
            TelegramBot bot = channelsBotProperties.getBotsChannelsMap().entrySet().stream()
                    .filter(entry -> entry.getValue().contains(chatId))
                    .map(Map.Entry::getKey)
                    .flatMap(botName ->
                            telegramBotList.stream()
                                    .filter(telegramBot -> botName.equalsIgnoreCase(telegramBot.getBotUsername()))
                    )
                    .findFirst()
                    .orElseGet(telegramBotList::getFirst);
            return bot;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

}
