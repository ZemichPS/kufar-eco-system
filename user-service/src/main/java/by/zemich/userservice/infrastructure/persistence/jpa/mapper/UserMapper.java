package by.zemich.userservice.infrastructure.persistence.jpa.mapper;

import by.zemich.userservice.domain.model.organization.vo.OrganizationId;
import by.zemich.userservice.domain.model.user.entity.User;
import by.zemich.userservice.domain.model.user.vo.*;
import by.zemich.userservice.infrastructure.persistence.jpa.entities.UserEntity;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UserMapper {
    public static User map(UserEntity entity) {
        User user = new User(new UserId(entity.getId()));
        user.setRegisteredAt(entity.getRegistrationDate());
        user.assignRole(Role.valueOf(entity.getRole().toString()));
        user.setFullName(new FullName(entity.getFirstName(), entity.getLastName()));
        user.setEmail(new Email(entity.getEmail()));
        user.setPhoneNumber(new PhoneNumber(entity.getPhoneNumber()));
        user.setExternalTelegramData(new ExternalTelegramData(
                entity.getTelegramUserId(),
                entity.getTelegramChatId(),
                entity.getTelegramUsername()
        ));
        user.setPassword(entity.getPassword());
        user.setEnabled(entity.getEnabled());
        if (entity.getOrganization() != null)
            user.setOrganizationId(new OrganizationId(entity.getOrganization().getId()));
        return user;
    }

    public static UserEntity map(User domain) {
        return UserEntity.builder()
                .id(domain.getUserId().id())
                .firstName(domain.getFullName().getFirstname())
                .lastName(domain.getFullName().getLastname())
                .registrationDate(domain.getRegisteredAt())
                .email(domain.getEmail().getEmail())
                .phoneNumber(domain.getPhoneNumber().phoneNumber())
                .telegramUserId(domain.getExternalTelegramData().getUserId())
                .telegramUsername(domain.getExternalTelegramData().getUsername())
                .telegramChatId(domain.getExternalTelegramData().getChatId())
                .password(domain.getPassword())
                .role(UserEntity.Role.valueOf(domain.getRole().toString()))
                .enabled(domain.isEnabled())
                .build();
    }
}
