package by.zemich.telegrambotservice.application.service.channels.api;

import by.zemich.telegrambotservice.application.service.NotificationPostManager;
import by.zemich.telegrambotservice.application.service.PostManager;
import by.zemich.telegrambotservice.application.service.bots.TelegramBotService;
import by.zemich.telegrambotservice.domain.model.KufarAdvertisement;
import by.zemich.telegrambotservice.domain.model.Notification;
import by.zemich.telegrambotservice.infrastructure.properties.ChannelsDelayProperty;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;

import java.util.Optional;

import static java.lang.Thread.sleep;


@Slf4j
public abstract class AbstractTelegramChannel extends Channel {
    private final TelegramBotService telegramBotService;
    private final PostManager postManager;
    private final NotificationPostManager notificationPostManager;
    private final ChannelsDelayProperty channelsDelayProperty;


    protected AbstractTelegramChannel(TelegramBotService telegramBotService,
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
    public boolean publish(KufarAdvertisement kufarAdvertisement) {
        if (!super.checkPolicies(kufarAdvertisement)) return false;
        doDelay();
        SendPhoto photoPost = postManager.create(kufarAdvertisement);
        photoPost.setChatId(getChannelId());
        telegramBotService.sendPhotoByChatId(photoPost);
        return true;
    }

    @Override
    public void notify(Notification notification) {
        SendPhoto photoPost = notificationPostManager.create(notification);
        photoPost.setChatId(getChannelId());
        telegramBotService.sendPhotoByChatId(photoPost);
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
