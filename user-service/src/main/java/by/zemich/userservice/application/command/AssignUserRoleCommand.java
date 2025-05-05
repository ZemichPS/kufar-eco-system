package by.zemich.userservice.application.command;

import by.zemich.userservice.domain.models.user.vo.Role;
import by.zemich.userservice.domain.models.user.vo.UserId;

public record AssignUserRoleCommand(
        UserId userId,
        Role role
) {
}
