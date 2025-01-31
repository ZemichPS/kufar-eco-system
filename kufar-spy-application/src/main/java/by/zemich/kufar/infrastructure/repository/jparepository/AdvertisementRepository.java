package by.zemich.kufar.infrastructure.repository.jparepository;

import by.zemich.kufar.domain.model.Advertisement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AdvertisementRepository extends JpaRepository<Advertisement, UUID> {

    boolean existsByAdId(Long adId);

    boolean existsByLink(String link);

    boolean existsByPublishedAtAndAdIdAndCategory(LocalDateTime localDateTime, Long adId, String category);

    @Query("SELECT a FROM Advertisement a WHERE a.publishedAt = :listTime AND a.adId = :adId AND a.category = :category ORDER BY a.publishedAt DESC LIMIT 1")
    Optional<Advertisement> getByPublishedAtdAndAdIdAndCategory(@Param("listTime") LocalDateTime listTime, @Param("adId") Long adId, @Param("category") String category);


    Advertisement findByLink(String link);
    
    @Query(value = """
            SELECT a.*
            FROM app.advertisements a
            WHERE a.parameters @> CAST(:parameters AS jsonb)
            """, nativeQuery = true)
    List<Advertisement> findAllByParameters(@Param("parameters") String parameters);

}
