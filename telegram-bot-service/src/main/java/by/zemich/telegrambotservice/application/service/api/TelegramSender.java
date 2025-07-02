package by.zemich.telegrambotservice.application.service.api;

public interface TelegramSender<T> {
    void send(T t);
}
