package by.zemich.device_catalog_service.utils;

import by.zemich.device_catalog_service.domen.dtos.ModelDto;
import by.zemich.device_catalog_service.domen.entities.ModelEntity;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ModelMapper {
    public static ModelDto map(ModelEntity modelEntity) {
        return ModelDto.builder()
                .uuid(modelEntity.getUuid())
                .name(modelEntity.getName())
                .build();
    }

    public static ModelEntity map(ModelDto dto) {
        return ModelEntity.builder()
                .uuid(dto.getUuid())
                .name(dto.getName())
                .build();
    }
}
