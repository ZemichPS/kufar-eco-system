package by.zemich.userservice.infrastructure.persistence.jpa.repositories;

import by.zemich.userservice.domain.dto.OrganizationFullProjection;
import by.zemich.userservice.domain.dto.OrganizationProjection;
import by.zemich.userservice.infrastructure.persistence.jpa.entities.OrganizationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrganizationViewRepository extends JpaRepository<OrganizationEntity, UUID> {

    Optional<OrganizationProjection> findProjectedById(UUID id);

    Optional<OrganizationProjection> findProjectedByOwnerId(UUID id);

    List<OrganizationFullProjection> findAllProjectedBy();
}
