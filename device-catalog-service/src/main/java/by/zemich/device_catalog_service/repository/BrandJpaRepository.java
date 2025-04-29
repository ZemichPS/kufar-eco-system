package by.zemich.device_catalog_service.repository;

import by.zemich.device_catalog_service.domen.entities.BrandEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface BrandJpaRepository extends JpaRepository<BrandEntity, UUID> {
}
