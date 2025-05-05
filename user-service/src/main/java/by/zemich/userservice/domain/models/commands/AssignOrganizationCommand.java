package by.zemich.userservice.domain.models.commands;

import by.zemich.userservice.domain.models.organization.vo.OrganizationId;
import by.zemich.userservice.domain.models.user.vo.UserId;

import java.util.UUID;

public record AssignOrganizationCommand(UserId userId, OrganizationId organizationId) {
}
