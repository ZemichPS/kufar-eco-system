package by.zemich.advertisementservice.infrastracture.output.repository.jpa.mapper;

import by.zemich.advertisementservice.domain.entity.Advertisement;
import by.zemich.advertisementservice.infrastracture.output.repository.jpa.entity.AdvertisementEntity;
import lombok.experimental.UtilityClass;

@UtilityClass
public class AdvertisementMapper {
    public static AdvertisementEntity mapToEntity(Advertisement advertisement) {
        return AdvertisementEntity.builder()
                .uuid(advertisement.getId().uuid())
                .userUuid(advertisement.getUserId().uuid())
                .condition(AdvertisementEntity.Condition.valueOf(advertisement.getCondition().name()))
                .publishedAt(advertisement.getPublishedAt())
                .activatedAt(advertisement.getActivatedAt())
                .priceInByn(advertisement.getPrice().priceInByn())
                .comment(advertisement.getComment().value())
                .photoFileName(advertisement.getPhoto().filename())
                .activatedAt(advertisement.getActivatedAt())
                .build();
    }
}
