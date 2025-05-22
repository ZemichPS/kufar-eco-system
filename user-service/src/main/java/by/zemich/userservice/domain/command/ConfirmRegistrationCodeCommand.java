package by.zemich.userservice.domain.command;

import by.zemich.userservice.domain.model.user.vo.UserId;

public record ConfirmRegistrationCodeCommand(UserId userId, String code) {
}
