package by.zemich.userservice.domain.models.user.entity;

import by.zemich.userservice.domain.models.commands.CreateUserCommand;
import by.zemich.userservice.domain.models.user.vo.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class User {
    private UserId userId;
    private LocalDateTime registeredAt;
    private Role role;
    private Name name;
    private Email email;
    private PhoneNumber phoneNumber;
    private String telegramUserId;
    private String password;
    private UUID organizationId;

    public User(CreateUserCommand command) {
        this.userId = new UserId(UUID.randomUUID());
        this.role = Role.valueOf(command.role());
        this.name = new Name(command.firstname(), command.lastname());
        this.email = new Email(command.email());
        this.phoneNumber = new PhoneNumber(command.phoneNumber());
    }

    public User(UserId userId) {
        this.userId = userId;
    }

    public void assignOrganization(UUID organizationId) {
        this.organizationId = organizationId;
    }


}
