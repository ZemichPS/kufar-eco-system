package by.zemich.telegrambotservice.domain.dto;

import lombok.Data;

@Data
public class UserRegistrationDto {
    private Long chatId;
    private Long userId;
    private String role;
    private String username;
    private String firstName;
    private String lastName;
    private String phoneNumber;

    public String toString() {
        return String.format("""
                Имя: %s
                Фамилия: %s
                Номер телефона: %s
                Роль: %s
                """, firstName, lastName, phoneNumber, role);
    }

}
