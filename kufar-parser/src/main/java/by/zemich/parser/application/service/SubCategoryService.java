package by.zemich.parser.application.service;

import by.zemich.parser.domain.model.Subcategory;
import by.zemich.parser.infrastructure.repository.jparepository.SubcategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SubCategoryService {
    private final SubcategoryRepository subCategoryRepository;

    @Transactional
    public Optional<Subcategory> getById(String id) {
        return subCategoryRepository.findById(id);
    }
}
