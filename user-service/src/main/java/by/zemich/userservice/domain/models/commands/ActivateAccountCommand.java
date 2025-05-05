package by.zemich.userservice.domain.models.commands;

import by.zemich.userservice.domain.models.user.vo.UserId;

public record ActivateAccountCommand(UserId userId) {
}
