package by.zemich.telegrambotservice.application.service.api;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static java.lang.Thread.sleep;

@Slf4j
public abstract class AbstractTelegramBot extends TelegramLongPollingBot {

    private final Lock lock = new ReentrantLock();

    public AbstractTelegramBot(String token) {
        super(token);
    }

      public void sendMessage(String chatId, String message) {
        try {
            execute(new SendMessage(chatId, message));
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendText(SendMessage message) {
        lock.lock();
        try {
            this.execute(message);
            sleep(1_200);
        } catch (TelegramApiException e) {
            log.error("Failed to SendMessage message to chatId {}, cause: {}", message.getChatId(), e.getMessage());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    public void sendPhoto(SendPhoto message) {
        try {
            this.execute(message);
        } catch (TelegramApiException e) {
            log.error("Failed to sendPhotoByChatId message to chatId {}, cause: {}", message.getChatId(), e.getMessage());
        }
    }

}
