package by.zemich.userservice.presenttion.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Data;
import java.util.UUID;

@Data
public class UserCreateDto {
    private String firstName;
    private String surname;
    private String email;
    private String telegramUserId;
    @Pattern(regexp = "^\\+375\\d{9}$", message = "Номер телефона должен начинаться с +375 и содержать 9 цифр")
    private String phoneNumber;
    private String password;
}


