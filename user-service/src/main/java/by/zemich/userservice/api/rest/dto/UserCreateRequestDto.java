package by.zemich.userservice.api.rest.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UserCreateRequestDto {
    private String firstName;
    private String lastName;
    @Email
    private String email;
    private String role;
    private String telegramUserId;
    @Pattern(regexp = "^\\+375\\d{9}$", message = "Номер телефона должен начинаться с +375 и содержать 9 цифр")
    private String phoneNumber;
    private String password;
}


