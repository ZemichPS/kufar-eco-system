package by.zemich.userservice.infrastructure.persistence.jpa.repositories;

import by.zemich.userservice.domain.model.organization.entity.Organization;
import by.zemich.userservice.domain.model.organization.vo.OrganizationId;
import by.zemich.userservice.domain.model.user.vo.UserId;
import by.zemich.userservice.domain.repository.OrganizationRepository;
import by.zemich.userservice.infrastructure.persistence.jpa.entities.OrganizationEntity;
import by.zemich.userservice.infrastructure.persistence.jpa.entities.UserEntity;
import by.zemich.userservice.infrastructure.persistence.jpa.mapper.OrganizationMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JpaOrganizationRepository implements OrganizationRepository {
    private final SpringDataOrganizationRepository organizationRepository;
    private final SpringDataUserRepository userRepository;

    @Override
    public Optional<Organization> findById(OrganizationId id) {
        return organizationRepository.findById(id.id()).map(OrganizationMapper::map);
    }

    @Override
    public Organization save(Organization organization) {
        OrganizationEntity organizationEntity = OrganizationMapper.map(organization);
        UserId userId = organization.getOwnerId();
        UserEntity user = userRepository.findById(userId.getId()).orElseThrow(() -> new EntityNotFoundException("User with id" + userId.getId() + " not found"));
        organizationEntity.setOwner(user);
        OrganizationEntity saved = organizationRepository.save(organizationEntity);
        return OrganizationMapper.map(saved);
    }

    @Override
    public List<Organization> getAllOrganizations() {
        return organizationRepository.findAll().stream()
                .map(OrganizationMapper::map)
                .toList();
    }
}
