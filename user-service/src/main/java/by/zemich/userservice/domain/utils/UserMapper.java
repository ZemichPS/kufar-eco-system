package by.zemich.userservice.domain.utils;

import by.zemich.userservice.domain.dto.UserResponseDto;
import by.zemich.userservice.infrastructure.persistence.jpa.entities.UserEntity;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UserMapper {
    public static UserResponseDto map(UserEntity entity) {
        return UserResponseDto.builder()
                .id(entity.getId())
                .email(entity.getEmail())
                .phone(entity.getPhoneNumber())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .build();
    }

}
