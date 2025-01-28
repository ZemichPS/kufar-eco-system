package by.zemich.kufar.application.service.channels.api;

import by.zemich.kufar.application.service.TelegramPostManager;
import by.zemich.kufar.application.service.api.NotificationPostManager;
import by.zemich.kufar.application.service.api.PhotoMessenger;
import by.zemich.kufar.application.service.api.PostManager;
import by.zemich.kufar.domain.model.Advertisement;
import by.zemich.kufar.domain.model.Notification;
import by.zemich.kufar.infrastructure.properties.ChannelsDelayProperty;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;

import java.util.Optional;

import static java.lang.Thread.sleep;


@Slf4j
public abstract class TelegramChannel extends Channel {
    private final PhotoMessenger<SendPhoto> photoMessenger;
    private final PostManager<SendPhoto, Advertisement> postManager;
    private final NotificationPostManager<SendPhoto, Notification> notificationPostManager;
    private final ChannelsDelayProperty channelsDelayProperty;


    protected TelegramChannel(PhotoMessenger<SendPhoto> photoMessenger,
                              PostManager<SendPhoto, Advertisement> postManager,
                              NotificationPostManager<SendPhoto, Notification> notificationPostManager,
                              ChannelsDelayProperty channelsDelayProperty
    ) {
        super();
        this.photoMessenger = photoMessenger;
        this.postManager = postManager;
        this.notificationPostManager = notificationPostManager;
        this.channelsDelayProperty = channelsDelayProperty;
    }

    @Override
    public boolean publish(Advertisement advertisement) {
        if (!super.checkPolicies(advertisement)) return false;
        doDelay();
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

    private void doDelay() {
        Optional.ofNullable(channelsDelayProperty.getDelayDurationByChannel(this))
                .filter(value -> value > 0)
                .ifPresent(delay -> {
                    log.info("Delay duration of {} seconds for channel {}", delay, this.getChannelName());
                    try {
                        sleep(delay);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                });
    }
}
