package by.zemich.parser.infrastructure.repository.jparepository;

import by.zemich.parser.domain.model.Category;
import by.zemich.parser.domain.model.Subcategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubcategoryRepository extends JpaRepository<Subcategory, String> {
    List<Subcategory> findAllByCategory(Category category);
}
