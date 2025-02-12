package by.zemich.parser.infrastructure.repository.jparepository;

import by.zemich.parser.domain.model.Category;
import by.zemich.parser.domain.model.Subcategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubcategoryRepository extends JpaRepository<Subcategory, String> {
    @Query("SELECT s FROM Subcategory s LEFT JOIN FETCH s.category WHERE s.category = :category")
    List<Subcategory> findAllByCategory(Category category);

    @Override
    @Query("SELECT s FROM Subcategory s LEFT JOIN FETCH s.category WHERE s.id = :categoryId")
    Optional<Subcategory> findById(String categoryId);
}
