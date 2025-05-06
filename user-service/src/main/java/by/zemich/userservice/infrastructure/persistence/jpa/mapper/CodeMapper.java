package by.zemich.userservice.infrastructure.persistence.jpa.mapper;

import by.zemich.userservice.domain.models.code.entity.EmailConfirmationCode;
import by.zemich.userservice.domain.models.code.vo.CodeId;
import by.zemich.userservice.domain.models.user.vo.UserId;
import by.zemich.userservice.infrastructure.persistence.jpa.entities.EmailConfirmationCodeEntity;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CodeMapper {

    public static EmailConfirmationCode mapToDomain(EmailConfirmationCodeEntity entity) {
        return EmailConfirmationCode.builder()
                .codeId(new CodeId(entity.getUuid()))
                .userId(new UserId(entity.getUserUuid()))
                .email(entity.getEmail())
                .code(entity.getCode())
                .expiresAt(entity.getExpiresAt())
                .confirmed(entity.isConfirmed())
                .build();
    }

    public static EmailConfirmationCodeEntity mapToEntity(EmailConfirmationCode code) {
        return EmailConfirmationCodeEntity.builder()
                .uuid(code.getCodeId().uuid())
                .userUuid(code.getUserId().getId())
                .email(code.getEmail())
                .code(code.getCode())
                .expiresAt(code.getExpiresAt())
                .confirmed(code.isConfirmed())
                .build();
    }
}
