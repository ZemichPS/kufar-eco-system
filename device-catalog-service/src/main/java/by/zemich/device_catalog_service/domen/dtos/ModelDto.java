package by.zemich.device_catalog_service.domen.dtos;


import lombok.*;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class ModelDto {
    private UUID uuid;
    private String name;

}
