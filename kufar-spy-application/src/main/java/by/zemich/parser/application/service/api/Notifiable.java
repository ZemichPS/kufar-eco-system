package by.zemich.parser.application.service.api;

import by.zemich.parser.domain.model.Notification;

public interface Notifiable {
    void notify(Notification notification);

    String getNotifierId();
}
