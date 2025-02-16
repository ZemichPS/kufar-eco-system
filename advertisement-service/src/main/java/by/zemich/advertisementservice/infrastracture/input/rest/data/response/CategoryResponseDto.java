package by.zemich.advertisementservice.infrastracture.input.rest.data.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class CategoryResponseDto {
    UUID uuid;
    String name;
    List<CategoryAttributeResponseDto> attributes;
}
