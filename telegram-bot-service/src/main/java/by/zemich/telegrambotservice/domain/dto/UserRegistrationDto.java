package by.zemich.telegrambotservice.domain.dto;

import lombok.Data;

@Data
public class UserRegistrationDto {
    private Long chatId;
    private Long userId;
    private String role;
    private String username;
    private String firstName;
    private String LastName;
    private String phoneNumber;

}
