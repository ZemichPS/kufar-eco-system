package by.zemich.userservice.application.command;

import by.zemich.userservice.domain.models.commands.RegisterUserCommand;
import by.zemich.userservice.domain.models.commands.AssignOrganizationCommand;
import by.zemich.userservice.domain.models.exceptions.OrganizationNotFoundException;
import by.zemich.userservice.domain.models.exceptions.UserNotFoundException;
import by.zemich.userservice.domain.models.organization.entity.Organization;
import by.zemich.userservice.domain.models.organization.vo.OrganizationId;
import by.zemich.userservice.domain.models.user.entity.User;
import by.zemich.userservice.domain.models.user.vo.UserId;
import by.zemich.userservice.domain.repository.OrganizationRepository;
import by.zemich.userservice.domain.repository.UserRepository;
import by.zemich.userservice.domain.service.UserConfirmationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserCommandService {
    private final UserRepository userRepository;
    private final OrganizationRepository organizationRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserConfirmationService userConfirmationService;

    public User handle(RegisterUserCommand command) {
        String encodedPassword = passwordEncoder.encode(command.rawPassword());
        User newUser = new User(command);
        newUser.setPassword(encodedPassword);
        newUser.setEnabled(false);
        userConfirmationService.sendConfirmationEmail(command.email(), newUser.getUserId());
        return userRepository.save(newUser);
    }

    public void handle(ActivateUserByEmailCommand command) {
        userConfirmationService.checkConfirmationCode(command.userId(), command.code());
    }

    public void handle(AssignUserRoleCommand command) {
        UserId userId = command.userId();
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId.getId().toString()));
        user.assignRole(command.role());
        userRepository.save(user);

    }

    public User handle(AssignOrganizationCommand command) {
        UserId userId = command.userId();
        OrganizationId organizationId = command.organizationId();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(command.userId().toString()));
        Organization organization = organizationRepository.findById(organizationId)
                .orElseThrow(() -> new OrganizationNotFoundException(command.organizationId().toString()));
        user.assignOrganization(organization.getOrganizationId());
        organization.addStaff(userId);
        organizationRepository.save(organization);
        return userRepository.save(user);
    }

}
