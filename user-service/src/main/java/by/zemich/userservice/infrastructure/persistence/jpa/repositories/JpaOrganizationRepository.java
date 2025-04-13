package by.zemich.userservice.infrastructure.persistence.jpa.repositories;

import by.zemich.userservice.domain.models.organization.entity.Organization;
import by.zemich.userservice.domain.models.organization.vo.OrganizationId;
import by.zemich.userservice.domain.repository.OrganizationRepository;
import by.zemich.userservice.infrastructure.persistence.jpa.entities.OrganizationEntity;
import by.zemich.userservice.infrastructure.persistence.jpa.mapper.OrganizationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JpaOrganizationRepository implements OrganizationRepository {
    private final SpringDataOrganizationRepository repository;

    @Override
    public Optional<Organization> findById(OrganizationId id) {
        return repository.findById(id.id()).map(OrganizationMapper::map);
    }

    @Override
    public Organization save(Organization organization) {
        OrganizationEntity entity = OrganizationMapper.map(organization);
        OrganizationEntity saved = repository.save(entity);
        return OrganizationMapper.map(saved);
    }

    @Override
    public List<Organization> getAllOrganizations() {
        return repository.findAll().stream()
                .map(OrganizationMapper::map)
                .toList();
    }
}
