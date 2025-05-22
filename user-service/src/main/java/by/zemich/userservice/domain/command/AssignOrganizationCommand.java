package by.zemich.userservice.domain.command;

import by.zemich.userservice.domain.model.organization.vo.OrganizationId;
import by.zemich.userservice.domain.model.user.vo.UserId;

public record AssignOrganizationCommand(UserId userId, OrganizationId organizationId) {
}
