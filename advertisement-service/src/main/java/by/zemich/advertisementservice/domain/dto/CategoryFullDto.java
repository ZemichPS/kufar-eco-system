package by.zemich.advertisementservice.domain.dto;

import by.zemich.advertisementservice.domain.valueobject.CategoryId;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class CategoryFullDto {
    private UUID id;
    private String name;
    private List<CategoryAttributeDto> attributes;
}
