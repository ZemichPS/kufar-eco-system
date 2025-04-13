package by.zemich.userservice.infrastructure.persistence.jpa.repositories;

import by.zemich.userservice.infrastructure.persistence.jpa.entities.OrganizationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SpringDataOrganizationRepository extends JpaRepository<OrganizationEntity, UUID> {
}
