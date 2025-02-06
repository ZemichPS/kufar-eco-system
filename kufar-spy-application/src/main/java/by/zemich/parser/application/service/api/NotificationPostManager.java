package by.zemich.parser.application.service.api;

public interface NotificationPostManager<T, S> {
    T create(S source);
}
