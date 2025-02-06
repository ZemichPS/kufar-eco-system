package by.zemich.parser.application.service.channels;

import by.zemich.parser.application.service.NotificationPostManager;
import by.zemich.parser.application.service.PostManager;
import by.zemich.parser.application.service.channels.api.TelegramChannel;
import by.zemich.parser.domain.model.Advertisement;
import by.zemich.parser.domain.policy.CategoryPolicy;
import by.zemich.parser.domain.policy.OnlyOriginalGoodsPolicy;
import by.zemich.parser.domain.policy.api.Policy;
import by.zemich.parser.infrastructure.properties.ChannelsDelayProperty;
import by.zemich.parser.infrastructure.telegram.bots.TelegramBotService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Profile({"prod","dev"})
public class MainChannel extends TelegramChannel {
    private final String CHANNEL_CHAT_ID = "-1002385506241";
    private final String CHANNEL_CHAT_NANE = "Выгодные объявления с Kufar";

    public MainChannel(TelegramBotService telegramBotService,
                       NotificationPostManager notificationPostManager,
                       PostManager postManager,
                       ChannelsDelayProperty channelsDelayProperty
    ) {
        super(telegramBotService, postManager, notificationPostManager, channelsDelayProperty);
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
