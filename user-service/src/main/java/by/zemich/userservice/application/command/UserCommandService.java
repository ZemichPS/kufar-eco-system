package by.zemich.userservice.application.command;

import by.zemich.userservice.domain.command.AssignUserRoleCommand;
import by.zemich.userservice.domain.command.ConfirmRegistrationCodeCommand;
import by.zemich.userservice.domain.command.RegisterUserCommand;
import by.zemich.userservice.domain.command.RegisterUserFromTelegramCommand;
import by.zemich.userservice.domain.exception.UserAlreadyExistsException;
import by.zemich.userservice.domain.exception.UserNotFoundException;
import by.zemich.userservice.domain.model.user.entity.User;
import by.zemich.userservice.domain.model.user.vo.UserId;
import by.zemich.userservice.domain.policy.UserActivationAllowedPolicy;
import by.zemich.userservice.domain.repository.UserRepository;
import by.zemich.userservice.domain.service.UserConfirmationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserCommandService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserConfirmationService userConfirmationService;

    public User handle(RegisterUserCommand command) {
        String email = command.email().getEmail();
        if (userRepository.existsByEmail(email)) throw new UserAlreadyExistsException(email);

        String encodedPassword = passwordEncoder.encode(command.rawPassword());
        User newUser = new User(command);
        newUser.setPassword(encodedPassword);
        userConfirmationService.sendConfirmationEmail(email, newUser.getUserId());
        return userRepository.save(newUser);
    }

    public User handle(RegisterUserFromTelegramCommand command) {
        String email = command.email().getEmail();
        if (userRepository.existsByEmail(email)) throw new UserAlreadyExistsException(email);

        String encodedPassword = passwordEncoder.encode(command.rawPassword());
        User newUser = new User(command);
        newUser.setPassword(encodedPassword);
        userConfirmationService.sendConfirmationEmail(email, newUser.getUserId());
        return userRepository.save(newUser);
    }

    public void handle(ConfirmRegistrationCodeCommand command) {
        UserId userId = command.userId();
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId.toString()));
        userConfirmationService.checkConfirmationCode(command.userId(), command.code());
        new UserActivationAllowedPolicy().apply(user);
        userRepository.save(user);
    }

    public void handle(AssignUserRoleCommand command) {
        UserId userId = command.userId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId.id().toString()));
        user.assignRole(command.role());
        userRepository.save(user);
    }

    private

}
