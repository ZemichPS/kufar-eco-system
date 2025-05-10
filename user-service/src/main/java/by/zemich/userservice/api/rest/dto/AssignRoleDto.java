package by.zemich.userservice.api.rest.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class AssignRoleDto {
    @NotEmpty(message = "Роль не может быть пустой")
    String role;
}
