package by.zemich.device_catalog_service.domen.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BrandModifyDto {
    @NotBlank(message = "Имя бренда не должно быть пустым")
    @Size(min = 2, max = 100, message = "Имя бренда должно содержать от 2 до 100 символов")
    private String brandName;
}
