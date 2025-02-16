package by.zemich.advertisementservice.infrastracture.input.rest.data.response;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class CategoryAttributeResponseDto {
    private UUID uuid;
    private String name;
}

