package by.zemich.userservice.domain.model.commands;

import by.zemich.userservice.domain.model.user.vo.UserId;

public record ActivateUserByEmailCommand(UserId userId, String code) {
}
