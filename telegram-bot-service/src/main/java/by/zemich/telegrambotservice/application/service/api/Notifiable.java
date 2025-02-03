package by.zemich.telegrambotservice.application.service.api;


import by.zemich.telegrambotservice.domain.model.Notification;

public interface Notifiable {
    void notify(Notification notification);

    String getNotifierId();
}
