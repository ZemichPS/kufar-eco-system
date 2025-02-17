package by.zemich.advertisementservice.infrastracture.output.repository.jpa.api;

import by.zemich.advertisementservice.infrastracture.output.repository.jpa.entity.AdvertisementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AdvertisementRepository extends JpaRepository<AdvertisementEntity, UUID> {
}
