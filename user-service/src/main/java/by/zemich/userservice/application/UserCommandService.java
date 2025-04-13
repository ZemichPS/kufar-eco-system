package by.zemich.userservice.application;

import by.zemich.userservice.domain.models.commands.CreateUserCommand;
import by.zemich.userservice.domain.models.commands.AssignOrganizationCommand;
import by.zemich.userservice.domain.models.exceptions.OrganizationNotFoundException;
import by.zemich.userservice.domain.models.exceptions.UserNotFoundException;
import by.zemich.userservice.domain.models.organization.entity.Organization;
import by.zemich.userservice.domain.models.organization.vo.OrganizationId;
import by.zemich.userservice.domain.models.user.entity.User;
import by.zemich.userservice.domain.models.user.vo.UserId;
import by.zemich.userservice.domain.repository.OrganizationRepository;
import by.zemich.userservice.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserCommandService {
    private final UserRepository userRepository;
    private final OrganizationRepository organizationRepository;
    private final PasswordEncoder passwordEncoder;

    public User handle(CreateUserCommand command) {
        String encodedPassword = passwordEncoder.encode(command.rawPassword());
        User newUser = new User(command);
        newUser.setPassword(encodedPassword);
        return userRepository.save(newUser);
    }

    public User handle(AssignOrganizationCommand command) {
        UserId userId = new UserId(command.userId());
        OrganizationId organizationId = new OrganizationId(command.organizationId());
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(command.userId().toString()));
        Organization organization = organizationRepository.findById(organizationId)
                .orElseThrow(() -> new OrganizationNotFoundException(command.organizationId().toString()));
        user.assignOrganization(organizationId.id());
        organization.addStaff(userId.getId());
        organizationRepository.save(organization);
        return userRepository.save(user);
    }


}
