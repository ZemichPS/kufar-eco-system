//package by.zemich.telegrambotservice.application.service.channels;
//
//import by.zemich.telegrambotservice.application.service.NotificationPostManager;
//import by.zemich.telegrambotservice.application.service.PostManager;
//import by.zemich.telegrambotservice.application.service.bots.TelegramBotService;
//import by.zemich.telegrambotservice.application.service.channels.api.AbstractTelegramChannel;
//import by.zemich.telegrambotservice.domain.model.Advertisement;
//import by.zemich.telegrambotservice.domain.policy.*;
//import by.zemich.telegrambotservice.domain.policy.api.Policy;
//import by.zemich.telegrambotservice.infrastructure.properties.ChannelsDelayProperty;
//import by.zemich.telegrambotservice.infrastructure.repository.filerepositories.CategoryPricelistFileRepository;
//import by.zemich.telegrambotservice.infrastructure.repository.filerepositories.ClothesBrandsFileRepository;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.context.annotation.Profile;
//import org.springframework.stereotype.Component;
//
//import java.math.BigDecimal;
//import java.util.List;
//
//@Slf4j
//@Component
//@Profile({"prod", "dev"})
//public class BrandedWomenClothesChannel extends AbstractTelegramChannel {
//
//    private final String CHANNEL_CHAT_ID = "-1002270323996";
//    private final String CHANNEL_CHAT_NANE = "Брендовая женская одежда";
//    private final ClothesBrandsFileRepository clothesBrandsFileRepository;
//    private final CategoryPricelistFileRepository categoryPricelistFileRepository;
//
//    protected BrandedWomenClothesChannel(
//            TelegramBotService telegramBotService,
//            PostManager postManager,
//            NotificationPostManager notificationPostManager,
//            ChannelsDelayProperty channelsDelayProperty,
//            ClothesBrandsFileRepository clothesBrandsFileRepository,
//            CategoryPricelistFileRepository categoryPricelistFileRepository
//    ) {
//        super(telegramBotService, postManager, notificationPostManager, channelsDelayProperty);
//        this.clothesBrandsFileRepository = clothesBrandsFileRepository;
//        this.categoryPricelistFileRepository = categoryPricelistFileRepository;
//    }
//
//
//    @Override
//    public boolean publish(Advertisement advertisement) {
//        return super.publish(advertisement);
//    }
//
//    @Override
//    public String getChannelName() {
//        return this.CHANNEL_CHAT_NANE;
//    }
//
//    @Override
//    public String getChannelId() {
//        return this.CHANNEL_CHAT_ID;
//    }
//
//    @Override
//    public String getNotifierId() {
//        return CHANNEL_CHAT_ID;
//    }
//
//    @Override
//    protected List<Policy<Advertisement>> createPolicies() {
//        try {
//            return List.of(new OnlyDefiniteCategory("8080")
//                            .or(new OnlyDefiniteCategory("8110"))
//                            .or(new OnlyDefiniteCategory("8100"))
//                            .or(new OnlyDefiniteCategory("8020")),
//                    new OnlyOwnersAds(),
//                    new OnlyOriginalGoodsPolicy(),
//                    new OnlyBrandClothesPolicy().or(new OnlyBrandWoomanShoesPolicy()),
//                    new OnlyDefinedClothingBrandPolicy(clothesBrandsRepository.get()),
//                    new MinPriceForNewGoodsPolicy(new BigDecimal(40)),
//                    new WomenClothingPricePolicy(categoryPricelistFileRepository.getCategoryClothesPriceList())
//                            .or(new WomenShoesPricePolicy(clothesBrandsFileRepository.getCategoryShoesPriceList()))
//            );
//        } catch (Exception e) {
//            log.error("Error initializing policies for channel: {}", getChannelName(), e);
//            throw e;
//        }
//
//    }
//}
