package by.zemich.userservice.domain.model.factories;

import by.zemich.userservice.domain.command.RegisterUserCommand;
import by.zemich.userservice.domain.command.RegisterUserFromTelegramCommand;
import by.zemich.userservice.domain.model.user.entity.User;
import by.zemich.userservice.domain.model.user.vo.UserId;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.UUID;

public class UserFactory {
    public User createFromCommand(RegisterUserFromTelegramCommand command) {
        User user = new User(new UserId(UUID.randomUUID()));
        user.setEnabled(true);
        user.setFullName(command.fullName());
        user.setEmail(command.email());
        user.setExternalTelegramData(command.externalTelegramData());
        user.setPhoneNumber(command.phoneNumber());
        user.setRole(command.role());
        user.setRegisteredAt(LocalDateTime.now());
        return user;
    }

    public User createFromCommand(RegisterUserCommand command, PasswordEncoder passwordEncoder) {
        User user = new User(new UserId(UUID.randomUUID()));
        String encodedPassword = passwordEncoder.encode(command.rawPassword());
        user.setEnabled(false);
        user.setFullName(command.fullName());
        user.setEmail(command.email());
        user.setPhoneNumber(command.phoneNumber());
        user.setRole(command.role());
        user.setRegisteredAt(LocalDateTime.now());
        user.setPassword(encodedPassword);
        return user;
    }
}
