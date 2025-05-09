package by.zemich.userservice.api.rest.mapper;

import by.zemich.userservice.api.rest.dto.UserCreateRequestDto;
import by.zemich.userservice.domain.model.commands.RegisterUserCommand;

public class UserCommandMapper {
    public static RegisterUserCommand map(UserCreateRequestDto dto) {
        return new RegisterUserCommand(
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
