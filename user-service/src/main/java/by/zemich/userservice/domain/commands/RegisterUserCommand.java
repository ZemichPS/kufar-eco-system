package by.zemich.userservice.domain.commands;

import by.zemich.userservice.domain.model.user.vo.FullName;
import by.zemich.userservice.domain.model.user.vo.PhoneNumber;
import by.zemich.userservice.domain.model.user.vo.Role;

public record RegisterUserCommand(
        FullName fullName,
        Role role,
        String email,
        PhoneNumber phoneNumber,
        String rawPassword
) {
}
