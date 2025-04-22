package by.zemich.advertisementservice.infrastracture.output.repository.jpa.mapper;

import by.zemich.advertisementservice.domain.entity.Advertisement;
import by.zemich.advertisementservice.domain.valueobject.*;
import by.zemich.advertisementservice.infrastracture.output.repository.jpa.entity.AdvertisementEntity;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;

@UtilityClass
public class AdvertisementMapper {
    public static AdvertisementEntity mapToEntity(Advertisement domain) {
        return AdvertisementEntity.builder()
                .uuid(domain.getId().uuid())
                .userUuid(domain.getUserId().uuid())
                .condition(AdvertisementEntity.Condition.valueOf(domain.getCondition().name()))
                .publishedAt(domain.getPublishedAt())
                .activatedAt(domain.getReactivatedAt())
                .priceInByn(domain.getPrice().priceInByn())
                .comment(domain.getComment().value())
                .photoFileName(domain.getPhoto().filename())
                .activatedAt(domain.getReactivatedAt())
                .attributes(new ArrayList<>())
                .active(domain.isActive())
                .build();
    }

    public static Advertisement mapToDomain(AdvertisementEntity entity) {
        return new Advertisement(
                new AdvertisementId(entity.getUuid()),
                new UserId(entity.getUserUuid()),
                new CategoryId(entity.getCategory().getUuid()),
                Condition.valueOf(entity.getCondition().name()),
                new Price(entity.getPriceInByn()),
                entity.getPublishedAt(),
                new Comment(entity.getComment()),
                entity.getActive(),
                new Photo(entity.getPhotoFileName()),
                Side.valueOf(entity.getSide().name())
        );
    }
}
