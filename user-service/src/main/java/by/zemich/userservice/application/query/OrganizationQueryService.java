package by.zemich.userservice.application.query;

import by.zemich.userservice.application.query.dto.OrganizationResponseDto;
import by.zemich.userservice.domain.model.organization.entity.Organization;
import by.zemich.userservice.domain.model.user.vo.UserId;
import by.zemich.userservice.domain.repository.OrganizationRepository;
import by.zemich.userservice.domain.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OrganizationQueryService {

    private final OrganizationRepository organizationRepository;

    public OrganizationResponseDto getByOwnerId(UserId userId) {
        return null;
    }

    public List<Organization> getAll() {
        return organizationRepository.getAllOrganizations();
    }


}
