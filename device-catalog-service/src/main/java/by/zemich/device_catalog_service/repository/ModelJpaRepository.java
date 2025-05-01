package by.zemich.device_catalog_service.repository;

import by.zemich.device_catalog_service.domen.entities.ModelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ModelJpaRepository extends JpaRepository<ModelEntity, UUID> {
    Optional<ModelEntity> findByName(String name);

    boolean existsByName(String name);
}
