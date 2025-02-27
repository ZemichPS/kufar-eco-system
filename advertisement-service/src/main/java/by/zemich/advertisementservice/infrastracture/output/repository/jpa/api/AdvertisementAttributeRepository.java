package by.zemich.advertisementservice.infrastracture.output.repository.jpa.api;

import by.zemich.advertisementservice.infrastracture.output.repository.jpa.entity.AdvertisementAttributeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AdvertisementAttributeRepository extends JpaRepository<AdvertisementAttributeEntity, UUID> {
}
