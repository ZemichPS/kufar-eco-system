package by.zemich.userservice.api.rest.mapper;

import by.zemich.userservice.application.query.dto.UserResponseDto;
import by.zemich.userservice.domain.models.user.entity.User;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UserMapper {
    public static UserResponseDto map(User user) {
        return UserResponseDto.builder()
                .id(user.getUserId().getId())
                .email(user.getEmail().getEmail())
                .phone(user.getPhoneNumber().phoneNumber())
                .firstName(user.getName().getFirstname())
                .lastName(user.getName().getLastname())
                .build();
    }

}
