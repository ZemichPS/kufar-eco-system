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
        user.setName(new Name(entity.getUsername(), entity.getFirstName(), entity.getLastName()));
        user.setEmail(new Email(entity.getEmail()));
        user.setPhoneNumber(new PhoneNumber(entity.getPhoneNumber()));
        user.setTelegramUserId(entity.getTelegramUserId());
        user.setPassword(entity.getPassword());
        user.setOrganizationId(new OrganizationId(entity.getOrganizationId()));
        user.setEnabled(entity.getEnabled());
        return user;
    }

    public static UserEntity map(User domain) {
        return UserEntity.builder()
                .id(domain.getUserId().getId())
                .username(domain.getName().getUsername())
                .firstName(domain.getName().getFirstname())
                .lastName(domain.getName().getLastname())
                .registrationDate(domain.getRegisteredAt())
                .email(domain.getEmail().getEmail())
                .telegramUserId(domain.getTelegramUserId())
                .password(domain.getPassword())
                .role(UserEntity.Role.valueOf(domain.getRole().toString()))
                .organizationId(domain.getOrganizationId() != null ? domain.getOrganizationId().id() : null)
                .enabled(domain.isEnabled())
                .build();
    }
}
