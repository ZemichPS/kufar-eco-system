package by.zemich.userservice.dao.repositories;

import by.zemich.userservice.dao.entities.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrganizationRepository extends JpaRepository<Organization, UUID> {
}
