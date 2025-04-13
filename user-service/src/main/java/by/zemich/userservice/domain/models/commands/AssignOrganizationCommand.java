package by.zemich.userservice.domain.models.commands;

import java.util.UUID;

public record AssignOrganizationCommand(UUID userId, UUID organizationId) {
}
