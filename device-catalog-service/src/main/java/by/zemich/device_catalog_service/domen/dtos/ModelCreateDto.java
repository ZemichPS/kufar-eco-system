package by.zemich.device_catalog_service.domen.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModelCreateDto {

    @NotBlank(message = "Имя модели не должно быть пустым")
    @Size(min = 2, max = 100, message = "Имя модели должно содержать от 2 до 100 символов")
    private String modelName;
}
