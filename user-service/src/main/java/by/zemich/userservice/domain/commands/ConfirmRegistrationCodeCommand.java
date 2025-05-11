package by.zemich.userservice.domain.commands;

import by.zemich.userservice.domain.model.user.vo.UserId;

public record ConfirmRegistrationCodeCommand(UserId userId, String code) {
}
