package by.zemich.userservice.application.query;

import by.zemich.userservice.domain.dto.OrganizationDto;
import by.zemich.userservice.domain.dto.OrganizationFullDto;
import by.zemich.userservice.domain.model.organization.entity.Organization;
import by.zemich.userservice.domain.model.organization.vo.OrganizationId;
import by.zemich.userservice.domain.model.user.vo.UserId;
import by.zemich.userservice.domain.repository.OrganizationQueryRepository;
import by.zemich.userservice.domain.repository.OrganizationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OrganizationQueryService {

    private final OrganizationQueryRepository organizationQueryRepository;

    public OrganizationDto getByOwnerId(UserId userId) {
        return organizationQueryRepository.findByOwnerId(userId);
    }

    public OrganizationDto getById(OrganizationId organizationId) {
        return organizationQueryRepository.findById(organizationId);
    }

    public List<OrganizationFullDto> getAll() {
        return organizationQueryRepository.findAll();
    }


}
