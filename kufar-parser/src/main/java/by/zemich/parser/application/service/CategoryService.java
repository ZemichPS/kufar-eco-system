package by.zemich.parser.application.service;

import by.zemich.parser.domain.model.Category;
import by.zemich.parser.domain.model.Subcategory;
import by.zemich.parser.infrastructure.repository.jparepository.CategoryRepository;
import by.zemich.parser.infrastructure.repository.jparepository.SubcategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final SubcategoryRepository subcategoryRepository;

    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    public Optional<Category> getById(String id) {
        return categoryRepository.findById(id);
    }

    public Optional<Category> getByCategoryName(String categoryName) {
        return categoryRepository.findByName(categoryName);
    }

    public List<Subcategory> getAllSubcategoriesByCategory(Category category) {
        return subcategoryRepository.findAllByCategory(category);
    }

    public List<Subcategory> getAllSubcategories() {
        return subcategoryRepository.findAll();
    }
}
