package by.zemich.userservice.api.rest.dto;

import lombok.Data;

@Data
public class UserRegistrationDto {
    private Long chatId;
    private Long userId;
    private String username;
    private String email;
    private String role;
    private String firstName;
    private String lastName;
    private String phoneNumber;
}
