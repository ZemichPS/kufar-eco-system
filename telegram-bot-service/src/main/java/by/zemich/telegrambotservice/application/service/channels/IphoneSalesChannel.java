package by.zemich.telegrambotservice.application.service.channels;

import by.zemich.telegrambotservice.application.service.NotificationPostManager;
import by.zemich.telegrambotservice.application.service.PostManager;
import by.zemich.telegrambotservice.application.service.bots.TelegramBotService;
import by.zemich.telegrambotservice.application.service.channels.api.AbstractTelegramChannel;
import by.zemich.telegrambotservice.domain.model.KufarAdvertisement;
import by.zemich.telegrambotservice.domain.policy.*;
import by.zemich.telegrambotservice.domain.policy.api.ExcludeModelsPolicy;
import by.zemich.telegrambotservice.domain.policy.api.Policy;
import by.zemich.telegrambotservice.domain.service.PriceAnalyzer;
import by.zemich.telegrambotservice.infrastructure.properties.ChannelsDelayProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;


@Component
@ConditionalOnProperty(prefix = "channels.iphone-sales", name = "enabled", havingValue = "true")
public class IphoneSalesChannel extends AbstractTelegramChannel {
    private final String CHANNEL_CHAT_ID = "-1002312425178";
    private final String CHANNEL_CHAT_NANE = "Apple с kufar";
    private final PriceAnalyzer priceAnalyzer;

    public IphoneSalesChannel(TelegramBotService telegramBotService,
                              PostManager postManager,
                              NotificationPostManager notificationPostManager,
                              ChannelsDelayProperty channelsDelayProperty,
                              PriceAnalyzer priceAnalyzer
    ) {
        super(
                telegramBotService,
                postManager,
                notificationPostManager,
                channelsDelayProperty
        );
        this.priceAnalyzer = priceAnalyzer;
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
    protected List<Policy<KufarAdvertisement>> createPolicies() {
        return List.of(
                new CategoryPolicy("Мобильные телефоны"),
                new OnlyOriginalGoodsPolicy(),
                new MinPercentagePolicy(
                        BigDecimal.valueOf(-24),
                        priceAnalyzer
                ),
                new OnlyDefiniteSmartphoneBrandAdsPolicy("Apple"),
                new ExcludeModelsPolicy(List.of(
                        "iPhone 5",
                        "iPhone 5s",
                        "iPhone 5G",
                        "iPhone 6",
                        "iPhone 6s",
                        "iPhone 6 Plus",
                        "iPhone 7",
                        "iPhone 7 Plus",
                        "iPhone 8",
                        "iPhone 8 Plus",
                        "iPhone X",
                        "iPhone XR",
                        "iPhone XS",
                        "iPhone XS Max",
                        "iPhone SE (2020)"
                )),
                new OnlyCorrectModelPolicy());
    }
}
