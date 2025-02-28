package by.zemich.advertisementservice.infrastracture.output.repository.jpa.api;

import by.zemich.advertisementservice.infrastracture.output.repository.jpa.entity.AdvertisementEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AdvertisementRepository extends JpaRepository<AdvertisementEntity, UUID>, PagingAndSortingRepository<AdvertisementEntity, UUID> {
    List<AdvertisementEntity> findAllWithEagerRelationshipsByActiveIs(Pageable pageable, boolean activeIs);

    List<AdvertisementEntity> findAllByUserUuid(UUID userUuid);

    @Query("""
        SELECT DISTINCT a FROM AdvertisementEntity a
        JOIN a.attributes attr
        WHERE attr.categoryAttribute.uuid IN :categoryAttributeIds
        AND attr.value IN :values
        AND a.side = :side
    """)
    List<AdvertisementEntity> findByAttributesAndSide(
            @Param("categoryAttributeIds") List<UUID> categoryAttributeIds,
            @Param("values") List<String> values,
            @Param("side") String side);

}
