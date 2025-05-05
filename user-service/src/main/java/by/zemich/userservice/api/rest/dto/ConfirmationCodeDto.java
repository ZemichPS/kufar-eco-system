package by.zemich.userservice.api.rest.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NonNull;
import org.springframework.web.bind.annotation.PathVariable;

@Data
public class ConfirmationCodeDto {
    @NotEmpty(message = "Код подтверждения email не может быть пустым")
    private String code;
}
