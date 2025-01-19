package by.zemich.kufar.application.service.api;

public interface NotificationPostManager<T, S> {
    T create(S source);
}
