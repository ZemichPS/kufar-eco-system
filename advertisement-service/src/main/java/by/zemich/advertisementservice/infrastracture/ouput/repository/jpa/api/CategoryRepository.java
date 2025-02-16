package by.zemich.advertisementservice.infrastracture.ouput.repository.jpa.api;

import by.zemich.advertisementservice.infrastracture.ouput.repository.jpa.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, UUID> {
}
