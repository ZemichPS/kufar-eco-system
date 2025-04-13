package by.zemich.userservice.domain.repository;

import by.zemich.userservice.domain.models.organization.entity.Organization;
import by.zemich.userservice.domain.models.organization.vo.OrganizationId;

import java.util.Optional;

public interface OrganizationRepository {
    Optional<Organization> findById(OrganizationId id);
    Organization save(Organization organization);
}
