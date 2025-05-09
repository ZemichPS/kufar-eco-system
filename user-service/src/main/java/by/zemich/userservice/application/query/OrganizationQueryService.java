package by.zemich.userservice.application.query;

import by.zemich.userservice.domain.model.organization.entity.Organization;
import by.zemich.userservice.domain.repository.OrganizationRepository;
import by.zemich.userservice.domain.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class OrganizationQueryService {

    private final OrganizationRepository organizationRepository;
    private final UserRepository userRepository;

    public Organization getByOwnerId(UUID ownerId) {
        return null;
    }

    public List<Organization> getAll() {
        return organizationRepository.getAllOrganizations();
    }


}
