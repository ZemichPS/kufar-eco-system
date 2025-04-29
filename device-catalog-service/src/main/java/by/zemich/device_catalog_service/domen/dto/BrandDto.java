package by.zemich.device_catalog_service.domen.dto;

import lombok.*;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class BrandDto {
    private UUID uuid;
    private String name;
    private List<ModelDto> models;
}
