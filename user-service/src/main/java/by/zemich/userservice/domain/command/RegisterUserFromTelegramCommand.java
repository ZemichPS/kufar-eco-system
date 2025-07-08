package by.zemich.userservice.domain.command;

import by.zemich.userservice.domain.model.user.vo.*;

public record RegisterUserFromTelegramCommand(
        FullName fullName,
        Role role,
        Email email,
        PhoneNumber phoneNumber,
        ExternalTelegramData externalTelegramData
) {
}
