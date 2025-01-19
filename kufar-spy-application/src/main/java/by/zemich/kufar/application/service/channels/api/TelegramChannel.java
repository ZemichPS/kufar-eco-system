package by.zemich.kufar.application.service.channels.api;

import by.zemich.kufar.application.service.TelegramPostManager;
import by.zemich.kufar.application.service.api.NotificationPostManager;
import by.zemich.kufar.application.service.api.PhotoMessenger;
import by.zemich.kufar.application.service.api.PostManager;
import by.zemich.kufar.domain.model.Advertisement;
import by.zemich.kufar.domain.model.Notification;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;



public abstract class TelegramChannel extends Channel {
    private final PhotoMessenger<SendPhoto> photoMessenger;
    private final PostManager<SendPhoto, Advertisement> postManager;
    private final NotificationPostManager<SendPhoto, Notification> notificationPostManager;

    protected TelegramChannel(PhotoMessenger<SendPhoto> photoMessenger,
                              PostManager<SendPhoto, Advertisement> postManager,
                              NotificationPostManager<SendPhoto, Notification> notificationPostManager
    ) {
        super();
        this.photoMessenger = photoMessenger;
        this.postManager = postManager;
        this.notificationPostManager = notificationPostManager;
    }

    @Override
    public boolean publish(Advertisement advertisement) {
        if (!super.checkPolicies(advertisement)) return false;
        SendPhoto photoPost = postManager.create(advertisement);
        photoPost.setChatId(getChannelId());
        photoMessenger.sendPhoto(photoPost);
        return true;
    }

    @Override
    public void notify(Notification notification) {
        SendPhoto photoPost = notificationPostManager.create(notification);
        photoPost.setChatId(getChannelId());
        photoMessenger.sendPhoto(photoPost);
    }
}
