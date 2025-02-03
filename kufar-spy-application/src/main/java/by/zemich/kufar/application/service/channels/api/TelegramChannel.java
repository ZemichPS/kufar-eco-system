package by.zemich.kufar.application.service.channels.api;

import by.zemich.kufar.application.service.NotificationPostManager;
import by.zemich.kufar.application.service.PostManager;
import by.zemich.kufar.application.service.api.PhotoMessenger;
import by.zemich.kufar.domain.model.Advertisement;
import by.zemich.kufar.domain.model.Notification;
import by.zemich.kufar.infrastructure.properties.ChannelsDelayProperty;
import by.zemich.kufar.infrastructure.telegram.bots.TelegramBotService;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;

import java.util.Optional;

import static java.lang.Thread.sleep;


@Slf4j
public abstract class TelegramChannel extends Channel {
    private final TelegramBotService telegramBotService;
    private final PostManager postManager;
    private final NotificationPostManager notificationPostManager;
    private final ChannelsDelayProperty channelsDelayProperty;


    protected TelegramChannel(TelegramBotService telegramBotService,
                              PostManager postManager,
                              NotificationPostManager notificationPostManager,
                              ChannelsDelayProperty channelsDelayProperty
    ) {
        super();
        this.telegramBotService = telegramBotService;
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
        telegramBotService.sendPhoto(photoPost);
        return true;
    }

    @Override
    public void notify(Notification notification) {
        SendPhoto photoPost = notificationPostManager.create(notification);
        photoPost.setChatId(getChannelId());
        telegramBotService.sendPhoto(photoPost);
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
