package by.zemich.userservice.application.command;

import by.zemich.userservice.domain.models.user.vo.UserId;

public record ActivateUserByEmailCommand(UserId userId, String code) {
}
