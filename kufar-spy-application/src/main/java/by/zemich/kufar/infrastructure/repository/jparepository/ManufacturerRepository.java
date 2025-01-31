package by.zemich.kufar.infrastructure.repository.jparepository;

import by.zemich.kufar.domain.model.Manufacturer;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ManufacturerRepository extends JpaRepository<Manufacturer, Long> {
    @NotNull Optional<Manufacturer> findById(@NotNull Long id);
    Optional<Manufacturer> findByName(String name);

}
