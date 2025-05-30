package by.zemich.userservice.domain.command;

import by.zemich.userservice.domain.model.user.vo.Email;
import by.zemich.userservice.domain.model.user.vo.FullName;
import by.zemich.userservice.domain.model.user.vo.PhoneNumber;
import by.zemich.userservice.domain.model.user.vo.Role;

public record RegisterUserCommand(
        FullName fullName,
        Role role,
        Email email,
        PhoneNumber phoneNumber,
        String rawPassword
) {
}
