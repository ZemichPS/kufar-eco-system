package by.zemich.userservice.domain.command;

import by.zemich.userservice.domain.model.user.vo.Role;
import by.zemich.userservice.domain.model.user.vo.UserId;

public record AssignUserRoleCommand(
        UserId userId,
        Role role
) {
}
