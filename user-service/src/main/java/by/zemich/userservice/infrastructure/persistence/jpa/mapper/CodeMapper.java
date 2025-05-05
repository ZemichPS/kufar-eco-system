package by.zemich.userservice.infrastructure.persistence.jpa.mapper;

import by.zemich.userservice.domain.models.code.entity.EmailConfirmationCode;
import by.zemich.userservice.infrastructure.persistence.jpa.entities.EmailConfirmationCodeEntity;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CodeMapper {

    public static EmailConfirmationCode mapToDomain(EmailConfirmationCodeEntity entity) {
        return EmailConfirmationCode.builder()
                .email(entity.getEmail())
                .code(entity.getCode())
                .id(entity.getUuid())
                .expiresAt(entity.getExpiresAt())
                .confirmed(entity.isConfirmed())
                .build();
    }

    public static EmailConfirmationCodeEntity mapToEntity(EmailConfirmationCode code) {
        return EmailConfirmationCodeEntity.builder()
                .email(code.getEmail())
                .code(code.getCode())
                .uuid(code.getId())
                .expiresAt(code.getExpiresAt())
                .confirmed(code.isConfirmed())
                .build();
    }
}
