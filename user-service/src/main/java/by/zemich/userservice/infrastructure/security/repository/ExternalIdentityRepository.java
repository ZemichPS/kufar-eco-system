package by.zemich.userservice.infrastructure.security.repository;

import by.zemich.userservice.infrastructure.security.entities.ExternalIdentity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ExternalIdentityRepository extends JpaRepository<ExternalIdentity, UUID> {
    Optional<ExternalIdentity> findByProviderNameAndProviderUserId(String providerUserId, String providerId);
}
