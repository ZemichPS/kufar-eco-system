package by.zemich.userservice.infrastructure.security.repository;

import by.zemich.userservice.infrastructure.security.entities.UserDetailsImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserDetailsRepository extends JpaRepository<UserDetailsImpl, UUID> {
    Optional<UserDetailsImpl> findByEmail(String username);
}
