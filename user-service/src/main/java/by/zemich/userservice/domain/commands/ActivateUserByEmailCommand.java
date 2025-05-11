package by.zemich.userservice.domain.commands;

import by.zemich.userservice.domain.model.user.vo.UserId;

public record ActivateUserByEmailCommand(UserId userId, String code) {
}
