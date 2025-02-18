package by.zemich.advertisementservice.infrastracture.output.repository.jpa.mapper;

import by.zemich.advertisementservice.domain.entity.Advertisement;
import by.zemich.advertisementservice.infrastracture.output.repository.jpa.entity.AdvertisementEntity;
import lombok.experimental.UtilityClass;

@UtilityClass
public class AdvertisementMapper {
    public static AdvertisementEntity mapToEntity(Advertisement domain) {
        return AdvertisementEntity.builder()
                .uuid(domain.getId().uuid())
                .userUuid(domain.getUserId().uuid())
                .condition(AdvertisementEntity.Condition.valueOf(domain.getCondition().name()))
                .publishedAt(domain.getPublishedAt())
                .activatedAt(domain.getActivatedAt())
                .priceInByn(domain.getPrice().priceInByn())
                .comment(domain.getComment().value())
                .photoFileName(domain.getPhoto().filename())
                .activatedAt(domain.getActivatedAt())
                .build();
    }

    public static Advertisement mapToDomain(AdvertisementEntity entity){
        return null;
    }
}
