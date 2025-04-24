package by.zemich.advertisementservice.domain.response;

import by.zemich.advertisementservice.domain.valueobject.CategoryAttributeId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryAttributeDto {
    private UUID uuid;
    private String name;
}
