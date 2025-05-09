package by.zemich.userservice.application.command;

import by.zemich.userservice.domain.model.commands.CreateOrganizationCommand;
import by.zemich.userservice.domain.exception.UserNotFoundException;
import by.zemich.userservice.domain.model.organization.entity.Organization;
import by.zemich.userservice.domain.model.user.vo.UserId;
import by.zemich.userservice.domain.repository.OrganizationRepository;
import by.zemich.userservice.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrganizationCommandService {

    private final OrganizationRepository organizationRepository;
    private final UserRepository userRepository;

    public Organization handle(CreateOrganizationCommand command) {
        UserId userId = command.ownerId();
        if(!userRepository.existsById(userId)) throw new UserNotFoundException(userId.getId().toString());
        Organization organization = new Organization(command);
        return organizationRepository.save(organization);
    }

}
