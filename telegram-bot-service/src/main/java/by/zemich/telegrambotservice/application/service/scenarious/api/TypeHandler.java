package by.zemich.telegrambotservice.application.service.scenarious.api;

public interface TypeHandler<T extends Enum<T>> {
    T getType();
}
