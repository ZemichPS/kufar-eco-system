package by.zemich.advertisementservice.domain.response;

import by.zemich.advertisementservice.domain.valueobject.CategoryId;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class CategoryFullDto {
    private CategoryId id;
    private String name;
    private List<CategoryAttributeDto> attributes;
}
