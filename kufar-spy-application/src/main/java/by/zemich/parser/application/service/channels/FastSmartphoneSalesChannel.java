package by.zemich.parser.application.service.channels;

import by.zemich.parser.application.service.MarketPriceService;
import by.zemich.parser.application.service.NotificationPostManager;
import by.zemich.parser.application.service.PostManager;
import by.zemich.parser.application.service.channels.api.TelegramChannel;
import by.zemich.parser.domain.model.Advertisement;
import by.zemich.parser.domain.policy.*;
import by.zemich.parser.application.service.AdvertisementService;
import by.zemich.parser.domain.policy.api.Policy;
import by.zemich.parser.domain.service.PriceAnalyzer;
import by.zemich.parser.infrastructure.properties.ChannelsDelayProperty;
import by.zemich.parser.infrastructure.telegram.bots.TelegramBotService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@Profile({"prod", "dev"})
public class FastSmartphoneSalesChannel extends TelegramChannel {
    private final String CHANNEL_CHAT_ID = "-1002499186724";
    private final String CHANNEL_CHAT_NANE = "Срочные продажи смартфонов";
    private final PriceAnalyzer priceAnalyzer;
    private final AdvertisementService advertisementService;
    private final MarketPriceService marketPriceService;

    public FastSmartphoneSalesChannel(TelegramBotService telegramBotService,
                                      PostManager postManager,
                                      PriceAnalyzer priceAnalyzer,
                                      AdvertisementService advertisementService,
                                      NotificationPostManager notificationPostManager,
                                      ChannelsDelayProperty channelsDelayProperty,
                                      MarketPriceService marketPriceService
    ) {
        super(telegramBotService, postManager, notificationPostManager, channelsDelayProperty);
        this.priceAnalyzer = priceAnalyzer;
        this.advertisementService = advertisementService;
        this.marketPriceService = marketPriceService;

    }

    @Override
    public boolean publish(Advertisement advertisement) {
        return super.publish(advertisement);
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
