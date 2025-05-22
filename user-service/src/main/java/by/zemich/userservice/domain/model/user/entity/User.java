package by.zemich.userservice.domain.model.user.entity;

import by.zemich.userservice.domain.command.RegisterUserCommand;
import by.zemich.userservice.domain.model.organization.vo.OrganizationId;
import by.zemich.userservice.domain.model.user.vo.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class User {
    private UserId userId;
    private LocalDateTime registeredAt;
    @Setter(AccessLevel.NONE)
    private Role role;
    @Setter
    private FullName fullName;
    private Email email;
    private PhoneNumber phoneNumber;
    private String telegramUserId;
    private String password;
    private OrganizationId organizationId;
    private boolean enabled;

    public User(RegisterUserCommand command) {
        this.userId = new UserId(UUID.randomUUID());
        this.role = command.role();
        this.fullName = command.fullName();
        this.email = new Email(command.email());
        this.phoneNumber = getPhoneNumber();
    }

    public User(UserId userId) {
        this.userId = userId;
    }

    public void assignOrganization(OrganizationId organizationId) {
        this.organizationId = organizationId;
    }

    public void assignRole(Role role) {
        this.role = role;
    }


}
