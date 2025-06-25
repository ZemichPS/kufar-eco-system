package by.zemich.telegrambotservice.application.service;

public interface TelegramSender<T> {
    public void send(T t);
}
