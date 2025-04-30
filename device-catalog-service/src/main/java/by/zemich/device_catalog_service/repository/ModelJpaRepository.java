package by.zemich.device_catalog_service.repository;

import by.zemich.device_catalog_service.domen.entities.Model;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ModelJpaRepository extends JpaRepository<Model, UUID> {
    Optional<Model> findByName(String name);

    boolean existsByName(String name);
}
