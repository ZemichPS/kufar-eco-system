//package by.zemich.kufar.application.service.channels;
//
//import by.zemich.kufar.application.service.api.ClothesBrandsRepository;
//import by.zemich.kufar.application.service.channels.api.TelegramChannel;
//import by.zemich.kufar.domain.model.Advertisement;
//import by.zemich.kufar.domain.policy.*;
//import by.zemich.kufar.domain.policy.api.Policy;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.context.annotation.Profile;
//import org.springframework.stereotype.Component;
//
//import java.math.BigDecimal;
//import java.util.List;
//
//@Slf4j
//@Component
//@Profile({"prod","dev"})
//public class BrandedWomenClothesChannel extends TelegramChannel {
//
//    private final String CHANNEL_CHAT_ID = "-1002270323996";
//    private final String CHANNEL_CHAT_NANE = "Брендовая женская одежда";
//    private final ClothesBrandsRepository clothesBrandsRepository;
//    private final CategoryPriceListRepository categoryPriceListRepository;
//
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
//            return List.of(new OnlyDefiniteCategory("8110")
//                            .or(new OnlyDefiniteCategory("8080"))
//                            .or(new OnlyDefiniteCategory("8100"))
//                            .or(new OnlyDefiniteCategory("8020")),
//                    new OnlyOwnersAds(),
//                    new OnlyOriginalGoodsPolicy(),
//                    new OnlyBrandClothesPolicy().or(new OnlyBrandWoomanShoesPolicy()),
//                    new OnlyDefinedClothingBrandPolicy(clothesBrandsRepository.get()),
//                    new MinPriceForNewGoodsPolicy(new BigDecimal(40)),
//                    new WomenClothingPricePolicy(categoryPriceListRepository.getCategoryClothesPriceList())
//                            .or(new WomenShoesPricePolicy(categoryPriceListRepository.getCategoryShoesPriceList()))
//            );
//        } catch (Exception e) {
//            log.error("Error initializing policies for channel: {}", getChannelName(), e);
//            throw e;
//        }
//
//    }
//}
