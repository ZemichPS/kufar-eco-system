package by.zemich.device_catalog_service.utils;

import by.zemich.device_catalog_service.domen.dto.BrandDto;
import by.zemich.device_catalog_service.domen.entities.BrandEntity;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;

@UtilityClass
public class BrandMapper {

    public static BrandDto map(BrandEntity entity) {
        return BrandDto.builder()
                .uuid(entity.getUuid())
                .name(entity.getName())
                .models(new ArrayList<>())
                .build();
    }

    public static BrandEntity map(BrandDto dto) {
        return BrandEntity.builder()
                .uuid(dto.getUuid())
                .name(dto.getName())
                .modelEntities(new ArrayList<>())
                .build();
    }
}
