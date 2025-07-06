package by.zemich.telegrambotservice.application.service.scenarious.api;

public interface StateHandleable<T extends Enum<T>> {

    T getType();
}
