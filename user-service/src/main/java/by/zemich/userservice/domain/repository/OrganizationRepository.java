package by.zemich.userservice.domain.repository;

import by.zemich.userservice.domain.model.organization.entity.Organization;
import by.zemich.userservice.domain.model.organization.vo.OrganizationId;

import java.util.List;
import java.util.Optional;

public interface OrganizationRepository {

    Optional<Organization> findById(OrganizationId id);

    Organization save(Organization organization);

    List<Organization> getAllOrganizations();
}
