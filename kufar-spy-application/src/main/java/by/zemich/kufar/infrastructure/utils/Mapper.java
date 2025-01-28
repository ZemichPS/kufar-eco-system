package by.zemich.kufar.infrastructure.utils;

import by.zemich.kufar.infrastructure.clients.dto.*;
import by.zemich.kufar.application.service.api.MarketService;
import by.zemich.kufar.infrastructure.clients.ManufacturerDto;
import by.zemich.kufar.domain.model.*;
import lombok.experimental.UtilityClass;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@UtilityClass
public class Mapper {
    public static Advertisement mapToEntity(AdsDTO.AdDTO source) {
        return Advertisement.builder()
                .adId(source.getAdId())
                .link(source.getAdLink())
                .images(source.getImages().stream()
                        .map(AdsDTO.ImageDTO::getPath)
                        .collect(Collectors.joining(";"))
                )
                .category(source.getCategory())
                .companyAd(source.isCompanyAd())
                .publishedAt(source.getListTime())
                .subject(source.getSubject())
                .type(source.getType())
                .currency(source.getCurrency())
                .parameters(new ArrayList<>())
                .priceInByn(source.getPriceByn())
                .priceInUsd(source.getPriceUsd())
                .fullyFunctional(false)
                .seller(new Seller(source.getAccountId()))
                .build();
    }

    public static Seller mapToEntity(FeedbackResponse feedbackResponse, String sellerId) {
        List<FeedbackResponse.Feedback> feedbacks = feedbackResponse.getFeedbacks();

        if(feedbacks.isEmpty()) return new Seller(sellerId);

        Double score = feedbacks.stream()
                .mapToDouble(feedback -> Double.parseDouble(feedback.getScore()))
                .average()
                .orElse(0.0);

        ZonedDateTime firstFeedbackTime = Objects.requireNonNull(feedbacks.stream()
                .map(FeedbackResponse.Feedback::getCreatedAt)
                .filter(Objects::nonNull)
                .sorted()
                .findFirst()
                .orElse(null));


        return Seller.builder()
                .rate(score.floatValue())
                .id(sellerId)
                .firstFeedbackCreatedAt(firstFeedbackTime.toLocalDateTime())
                .feedbackCount(feedbacks.size())
                .build();
    }

    public static Advertisement.Parameter mapToEntity(AdsDTO.AdParameterDTO source) {
        return Advertisement.Parameter.builder()
                .identity(source.getP())
                .label(source.getPl())
                .value(source.getVl())
                .build();
    }

    public static GeoData mapToEntity(GeoDataDTO source) {
        return GeoData.builder()
                .id(source.getId())
                .pid(source.getPid())
                .name(source.getLabels().getRu())
                .type(source.getType())
                .tag(source.getTag())
                .region(source.getRegion())
                .area(source.getArea())
                .build();
    }

    public static Manufacturer mapToEntity(ManufacturerDto source) {
        return Manufacturer.builder()
                .id(source.getId())
                .name(source.getName())
                .models(new ArrayList<>())
                .build();
    }

    public static Model mapToEntity(ManufacturerDto.ModelDto source) {
        return Model.builder()
                .name(source.getName())
                .kufarId(source.getKufarId())
                .build();
    }


    public static MarketService.ProductDataDto mapToDto(Century21stGoodsPageDTO.ProductDTO source) {
        return new MarketService.ProductDataDto(source.getLink(), source.getPrice());
    }

    public static Category mapToEntity(CategoriesDto.CategoryDto source) {
        return Category.builder()
                .id(source.getId())
                .name(source.getName())
                .version(source.getVersion())
                .ordered(source.getOrder())
                .subcategories(new ArrayList<>())
                .build();
    }

    public static Subcategory mapToEntity(CategoriesDto.CategoryDto.SubcategoryDto source) {
        return Subcategory.builder()
                .id(source.getId())
                .ordered(source.getOrder())
                .name(source.getName())
                .redirect(source.getRedirect())
                .build();
    }


}
