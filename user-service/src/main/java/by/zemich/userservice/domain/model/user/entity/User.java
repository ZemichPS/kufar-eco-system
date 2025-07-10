package by.zemich.userservice.domain.model.user.entity;

import by.zemich.userservice.domain.model.organization.vo.OrganizationId;
import by.zemich.userservice.domain.model.user.vo.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class User {
    private UserId userId;
    private LocalDateTime registeredAt;
    private Role role;
    private FullName fullName;
    private Email email;
    private PhoneNumber phoneNumber;
    private String password;
    private ExternalTelegramData externalTelegramData;
    private OrganizationId organizationId;
    private boolean enabled;

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
