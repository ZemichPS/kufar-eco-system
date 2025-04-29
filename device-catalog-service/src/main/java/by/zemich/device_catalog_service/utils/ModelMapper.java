package by.zemich.device_catalog_service.utils;

import by.zemich.device_catalog_service.domen.dto.ModelDto;
import by.zemich.device_catalog_service.domen.entities.Model;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ModelMapper {
    public static ModelDto map(Model model) {
        return ModelDto.builder()
                .uuid(model.getUuid())
                .name(model.getName())
                .build();
    }

    public static Model map(ModelDto dto) {
        return Model.builder()
                .uuid(dto.getUuid())
                .name(dto.getName())
                .build();
    }
}
