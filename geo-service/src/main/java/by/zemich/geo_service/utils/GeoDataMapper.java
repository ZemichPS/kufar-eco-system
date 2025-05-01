package by.zemich.geo_service.utils;

import by.zemich.geo_service.domain.dtos.GeoDataDto;
import by.zemich.geo_service.domain.entities.GeoDataEntity;
import lombok.experimental.UtilityClass;

@UtilityClass
public class GeoDataMapper {

    public static GeoDataEntity map(GeoDataDto dto) {
        return GeoDataEntity.builder()
                .id(dto.getId())
                .name(dto.getName())
                .tag(dto.getTag())
                .pid(dto.getPid())
                .area(dto.getArea())
                .region(dto.getRegion())
                .type(dto.getType())
                .build();
    }

    public static GeoDataDto map(GeoDataEntity entity) {
        return GeoDataDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .tag(entity.getTag())
                .pid(entity.getPid())
                .area(entity.getArea())
                .region(entity.getRegion())
                .type(entity.getType())
                .build();
    }
}
