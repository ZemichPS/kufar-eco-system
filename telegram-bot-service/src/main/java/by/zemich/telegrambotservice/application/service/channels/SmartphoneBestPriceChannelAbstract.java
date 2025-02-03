package by.zemich.telegrambotservice.application.service.channels;

import by.zemich.telegrambotservice.application.service.MarketPriceService;
import by.zemich.telegrambotservice.domain.policy.MinPercentagePolicy;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;

import java.math.BigDecimal;
import java.util.List;


@Component
@Profile({"prod","dev"})
public class SmartphoneBestPriceChannel extends TelegramChannel {
    private final String CHANNEL_CHAT_ID = "-1002367745711";
    private final String CHANNEL_CHAT_NANE = "Лушие цены на смартфоны c куфар";
    private final PriceAnalyzer priceAnalyzer;
    private final AdvertisementService advertisementService;
    private final MarketPriceService marketPriceService;

    public SmartphoneBestPriceChannelAbstract(PhotoMessenger<SendPhoto> messenger,
                                              PostManager<SendPhoto,Advertisement> postManager,
                                              PriceAnalyzer priceAnalyzer,
                                              AdvertisementService advertisementService,
                                              NotificationPostManager<SendPhoto, Notification> notificationPostManager,
                                              ChannelsDelayProperty channelsDelayProperty, MarketPriceService marketPriceService
    ) {
        super(messenger, postManager, notificationPostManager, channelsDelayProperty);
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
                new CategoryPolicy("17010"),
                new OnlyOriginalGoodsPolicy(),
                new MinPercentagePolicy(
                        BigDecimal.valueOf(-35),
                        priceAnalyzer,
                        advertisementService,
                        marketPriceService
                ),
                new OnlyCorrectModelPolicy());
    }
}
