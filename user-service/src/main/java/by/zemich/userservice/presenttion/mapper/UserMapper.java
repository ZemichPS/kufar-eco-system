package by.zemich.userservice.presenttion.mapper;

import by.zemich.userservice.dao.entities.User;
import by.zemich.userservice.presenttion.dto.UserCreateDto;
import by.zemich.userservice.presenttion.dto.UserDto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UserMapper {
    public static User map(UserCreateDto dto) {
        return User.builder()
                .firstName(dto.getFirstName())
                .surname(dto.getSurname())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .telegramUserId(dto.getTelegramUserId())
                .phoneNumber(dto.getPhoneNumber())
                .build();
    }

    public static UserDto map(User user) {
        return UserDto.builder().build();
    }
}
