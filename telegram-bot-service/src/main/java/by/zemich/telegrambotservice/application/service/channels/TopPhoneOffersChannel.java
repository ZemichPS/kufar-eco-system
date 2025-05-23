package by.zemich.telegrambotservice.application.service.channels;

import by.zemich.telegrambotservice.application.service.NotificationPostManager;
import by.zemich.telegrambotservice.application.service.PostManager;
import by.zemich.telegrambotservice.application.service.bots.TelegramBotService;
import by.zemich.telegrambotservice.application.service.channels.api.AbstractTelegramChannel;
import by.zemich.telegrambotservice.domain.model.KufarAdvertisement;
import by.zemich.telegrambotservice.domain.policy.*;
import by.zemich.telegrambotservice.domain.policy.api.Policy;
import by.zemich.telegrambotservice.domain.service.PriceAnalyzer;
import by.zemich.telegrambotservice.infrastructure.properties.ChannelsDelayProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;


@Component
@ConditionalOnProperty(prefix = "channels.top-phone-offers", name = "enabled", havingValue = "true")
public class TopPhoneOffersChannel extends AbstractTelegramChannel {
    private final String CHANNEL_CHAT_ID = "-1002258198884";
    private final String CHANNEL_CHAT_NANE = "Смартфоны с полной функциональностью по выгодной цене";
    private final PriceAnalyzer priceAnalyzer;

    public TopPhoneOffersChannel(TelegramBotService telegramBotService,
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
                        BigDecimal.valueOf(-30),
                        priceAnalyzer
                ),
                new OnlyFullyFunctionalAdsPolicy(),
                new OnlyCorrectModelPolicy());
    }
}
