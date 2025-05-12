package by.zemich.userservice.application.command;

import by.zemich.userservice.domain.exception.OrganizationNotFoundException;
import by.zemich.userservice.domain.exception.UserNotFoundException;
import by.zemich.userservice.domain.commands.CreateOrganizationCommand;
import by.zemich.userservice.domain.commands.UpdateOrganizationCommand;
import by.zemich.userservice.domain.model.organization.entity.Organization;
import by.zemich.userservice.domain.model.organization.vo.Address;
import by.zemich.userservice.domain.model.organization.vo.OrganizationId;
import by.zemich.userservice.domain.model.user.vo.UserId;
import by.zemich.userservice.domain.repository.OrganizationRepository;
import by.zemich.userservice.domain.repository.UserRepository;
import by.zemich.userservice.domain.specification.UserOrganizationOwnerSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrganizationCommandService {

    private final OrganizationRepository organizationRepository;
    private final UserRepository userRepository;

    public Organization handle(CreateOrganizationCommand command) {
        UserId userId = command.ownerId();
        if (!userRepository.existsById(userId)) throw new UserNotFoundException(userId.id().toString());
        Organization organization = new Organization(command);
        return organizationRepository.save(organization);
    }

    public Organization handle(UpdateOrganizationCommand command) {
        OrganizationId organizationId = command.organizationId();
        UserId userId = command.userId();

        Organization organization = organizationRepository.findById(organizationId)
                .orElseThrow(() -> new OrganizationNotFoundException(organizationId.id().toString()));

        new UserOrganizationOwnerSpecification(userId).isSatisfiedBy(organization);

        organization.setOrganizationType(command.organizationType());
        organization.setName(command.name());
        organization.setAddress(Address.builder()
                .region(command.region())
                .district(command.district())
                .postalCode(command.postalCode())
                .city(command.city())
                .street(command.street())
                .houseNumber(command.houseNumber())
                .apartmentNumber(command.apartmentNumber())
                .build());
        organization.setPhoneNumber(command.phoneNumber());
        return organizationRepository.save(organization);
    }

}
