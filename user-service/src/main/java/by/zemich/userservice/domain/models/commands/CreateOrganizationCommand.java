package by.zemich.userservice.domain.models.commands;


import by.zemich.userservice.domain.models.organization.vo.OrganizationId;
import by.zemich.userservice.domain.models.user.vo.UserId;

import java.util.UUID;


public record CreateOrganizationCommand(
        OrganizationId organizationId,
        UserId ownerId,
        String name,
        String organizationType,
        String phoneNumber,
        String postalCode,
        String region,
        String district,        // Район (если применимо)
        String city,
        String street,
        String houseNumber,
        String apartmentNumber
) {
}
