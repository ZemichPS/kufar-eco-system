package by.zemich.advertisementservice.infrastracture.output.repository.elastic.mapper;

import by.zemich.advertisementservice.domain.dto.FullAdvertisementDto;
import by.zemich.advertisementservice.infrastracture.output.repository.elastic.documents.AdvertisementDocument;
import by.zemich.advertisementservice.infrastracture.output.repository.jpa.entity.AdvertisementEntity;
import lombok.experimental.UtilityClass;

@UtilityClass
public class AdvertisementMapper {

    public static FullAdvertisementDto map(AdvertisementDocument document) {
        return FullAdvertisementDto.builder()
                .uuid(document.getUuid())
                .categoryName(document.getCategoryName())
                .side(document.getSide())
                .comment(document.getComment())
                .priceInByn(document.getPriceInByn())
                .userUuid(document.getUserUuid())
                .publishedAt(document.getPublishedAt())
                .photoFileName(document.getPhotoFileName())
                .condition(document.getCondition())
                .active(document.getActive())
                .build();

    }

    public static AdvertisementDocument map(AdvertisementEntity entity) {
        return AdvertisementDocument.builder()
                .uuid(entity.getUuid())
                .categoryName(entity.getCategory().getName())
                .side(entity.getSide().name())
                .comment(entity.getComment())
                .priceInByn(entity.getPriceInByn())
                .userUuid(entity.getUserUuid())
                .publishedAt(entity.getPublishedAt())
                .photoFileName(entity.getPhotoFileName())
                .condition(entity.getCondition().getConditionDescription())
                .active(entity.getActive())
                .build();
    }

}
