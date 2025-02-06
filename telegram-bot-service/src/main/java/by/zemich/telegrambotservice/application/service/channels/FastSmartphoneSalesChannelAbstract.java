package by.zemich.telegrambotservice.application.service.channels;

import by.zemich.telegrambotservice.application.service.NotificationPostManager;
import by.zemich.telegrambotservice.application.service.PostManager;
import by.zemich.telegrambotservice.application.service.bots.TelegramBotService;
import by.zemich.telegrambotservice.application.service.channels.api.AbstractTelegramChannel;
import by.zemich.telegrambotservice.domain.model.KufarAdvertisement;
import by.zemich.telegrambotservice.domain.policy.*;
import by.zemich.telegrambotservice.domain.policy.api.Policy;
import by.zemich.telegrambotservice.infrastructure.properties.ChannelsDelayProperty;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@Profile({"prod", "dev"})
public class FastSmartphoneSalesChannelAbstract extends AbstractTelegramChannel {
    private final String CHANNEL_CHAT_ID = "-1002499186724";
    private final String CHANNEL_CHAT_NANE = "Срочные продажи смартфонов";

    protected FastSmartphoneSalesChannelAbstract(
            TelegramBotService telegramBotService,
            PostManager postManager,
            NotificationPostManager notificationPostManager,
            ChannelsDelayProperty channelsDelayProperty
    ) {
        super(telegramBotService,
                postManager,
                notificationPostManager,
                channelsDelayProperty
        );
    }


    @Override
    public boolean publish(KufarAdvertisement kufarAdvertisement) {
        return super.publish(kufarAdvertisement);
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
                new OnlyOriginalGoodsPolicy(),
                new CategoryPolicy("17010"),
                new MinPercentagePolicy(
                        BigDecimal.valueOf(-50),
                        priceAnalyzer,
                        advertisementService,
                        marketPriceService
                ),
                new OnlyCorrectModelPolicy(),
                new OnlyFullyFunctionalAdsPolicy(),
                new FastSalesPolicy()
        );
    }
}
