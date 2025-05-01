package by.zemich.geo_service.repositories;

import by.zemich.geo_service.domain.entities.GeoDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GeoDataRepository extends JpaRepository<GeoDataEntity, Integer> {
}
