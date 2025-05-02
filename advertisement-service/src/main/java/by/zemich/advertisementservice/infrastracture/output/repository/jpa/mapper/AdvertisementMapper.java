package by.zemich.advertisementservice.infrastracture.output.repository.jpa.mapper;

import by.zemich.advertisementservice.domain.dto.FullAdvertisementDto;
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
                .reactivatedAt(domain.getReactivatedAt())
                .priceInByn(domain.getPrice().priceInByn())
                .comment(domain.getComment().value())
                .photoFileName(domain.getPhoto().orElse(new Photo("")).filename())
                .side(AdvertisementEntity.Side.valueOf(domain.getSide().name()))
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

    public static FullAdvertisementDto mapToDto(AdvertisementEntity entity) {
        return FullAdvertisementDto.builder()
                .uuid(entity.getUuid())
                .priceInByn(entity.getPriceInByn())
                .userUuid(entity.getUserUuid())
                .categoryName(entity.getCategory().getName())
                .condition(entity.getCondition().getConditionDescription())
                .publishedAt(entity.getPublishedAt())
                .comment(entity.getComment())
                .side(entity.getSide().getDescription())
                .attributes(entity.getAttributes().stream()
                        .map(attributeEntity -> FullAdvertisementDto.AdvertisementAttributeDto.builder()
                                .name(attributeEntity.getCategoryAttribute().getName())
                                .value(attributeEntity.getValue())
                                .build())
                        .toList()
                )
                .build();
    }
}
