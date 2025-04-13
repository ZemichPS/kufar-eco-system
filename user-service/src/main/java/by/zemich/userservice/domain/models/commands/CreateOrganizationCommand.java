package by.zemich.userservice.domain.models.commands;


import java.util.UUID;


public record CreateOrganizationCommand(
        String name,
        String organizationType,
        UUID ownerId,
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
