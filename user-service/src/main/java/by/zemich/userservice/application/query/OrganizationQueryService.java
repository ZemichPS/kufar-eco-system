package by.zemich.userservice.application.query;

import by.zemich.userservice.domain.dto.OrganizationFullProjection;
import by.zemich.userservice.domain.dto.OrganizationProjection;
import by.zemich.userservice.domain.exception.OrganizationNotFoundException;
import by.zemich.userservice.domain.model.organization.vo.OrganizationId;
import by.zemich.userservice.domain.model.user.vo.UserId;
import by.zemich.userservice.infrastructure.persistence.jpa.repositories.OrganizationViewRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OrganizationQueryService {

    private final OrganizationViewRepository OrganizationViewRepository;

    public OrganizationProjection getByOwnerId(UserId userId) {
        return OrganizationViewRepository.findProjectedByOwnerId(userId.id())
                .orElseThrow(() -> new OrganizationNotFoundException(userId.toString()));
    }

    public OrganizationProjection getById(OrganizationId organizationId) {
        return OrganizationViewRepository.findProjectedById(organizationId.id())
                .orElseThrow(() -> new OrganizationNotFoundException(organizationId.toString()));

    }

    public List<OrganizationFullProjection> getAll() {
        return OrganizationViewRepository.findAllProjectedBy();
    }


}
