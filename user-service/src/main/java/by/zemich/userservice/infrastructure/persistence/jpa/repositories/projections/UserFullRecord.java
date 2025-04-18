package by.zemich.userservice.infrastructure.persistence.jpa.repositories.projections;

import by.zemich.userservice.infrastructure.persistence.jpa.entities.UserEntity;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserFullRecord(
        UUID id,
        String username,
        String firstName,
        String lastName,
        UserEntity.Role role,
        LocalDateTime registeredAt,
        String email,
        String telegramUserId,
        String phoneNumber,
        String password,
        UUID organizationId
) {
}
