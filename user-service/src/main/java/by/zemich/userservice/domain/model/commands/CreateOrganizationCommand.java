package by.zemich.userservice.domain.model.commands;


import by.zemich.userservice.domain.model.organization.vo.OrganizationId;
import by.zemich.userservice.domain.model.organization.vo.OrganizationType;
import by.zemich.userservice.domain.model.organization.vo.PhoneNumber;
import by.zemich.userservice.domain.model.user.vo.UserId;


public record CreateOrganizationCommand(
        OrganizationId organizationId,
        UserId ownerId,
        String name,
        String specialization,
        OrganizationType organizationType,
        PhoneNumber phoneNumber,
        String postalCode,
        String region,
        String district,        // Район (если применимо)
        String city,
        String street,
        String houseNumber,
        String apartmentNumber
) {
}
