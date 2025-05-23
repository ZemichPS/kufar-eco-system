package by.zemich.userservice.domain.dto;

import by.zemich.userservice.infrastructure.persistence.jpa.entities.UserEntity;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserFullRecord(
        UUID id,
        String firstName,
        String lastName,
        UserEntity.Role role,
        LocalDateTime registrationDate,
        String email,
        String telegramUserId,
        String phoneNumber,
        UUID organizationId
) {
}
