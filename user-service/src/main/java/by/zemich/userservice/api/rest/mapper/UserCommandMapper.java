package by.zemich.userservice.api.rest.mapper;

import by.zemich.userservice.api.rest.dto.UserCreateRequestDto;
import by.zemich.userservice.domain.models.commands.CreateUserCommand;

public class UserCommandMapper {
    public static CreateUserCommand map(UserCreateRequestDto dto) {
        return new CreateUserCommand(
                dto.getRole(),
                dto.getUsername(),
                dto.getFirstName(),
                dto.getLastName(),
                dto.getEmail(),
                dto.getPhoneNumber(),
                dto.getPassword()
        );
    }
}
