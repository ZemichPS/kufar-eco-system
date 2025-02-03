package by.zemich.telegrambotservice.application.service.channels;

import by.zemich.telegrambotservice.application.service.NotificationPostManager;
import by.zemich.telegrambotservice.application.service.PostManager;
import by.zemich.telegrambotservice.application.service.bots.TelegramBotService;
import by.zemich.telegrambotservice.application.service.channels.api.AbstractTelegramChannel;
import by.zemich.telegrambotservice.domain.model.Advertisement;
import by.zemich.telegrambotservice.domain.policy.CategoryPolicy;
import by.zemich.telegrambotservice.domain.policy.OnlyOriginalGoodsPolicy;
import by.zemich.telegrambotservice.domain.policy.api.Policy;
import by.zemich.telegrambotservice.infrastructure.properties.ChannelsDelayProperty;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;

import java.util.List;

@Component
@Profile({"prod","dev"})
public class MainChannel extends AbstractTelegramChannel {
    private final String CHANNEL_CHAT_ID = "-1002385506241";
    private final String CHANNEL_CHAT_NANE = "Выгодные объявления с Kufar";

    public MainChannel(TelegramBotService messenger,
                       NotificationPostManager notificationPostManager,
                       PostManager postManager,
                       ChannelsDelayProperty channelsDelayProperty
    ) {
        super(messenger, postManager, notificationPostManager, channelsDelayProperty);
    }

    @Override
    public String getChannelName() {
        return this.CHANNEL_CHAT_NANE;
    }

    @Override
    public String getChannelId() {
        return this.CHANNEL_CHAT_ID;
    }

    @Override
    public String getNotifierId() {
        return CHANNEL_CHAT_ID;
    }

    @Override
    protected List<Policy<Advertisement>> createPolicies() {
        return List.of(
                new OnlyOriginalGoodsPolicy(),
                new CategoryPolicy("17010")
        );
    }
}
