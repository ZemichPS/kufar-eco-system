package by.zemich.advertisementservice.infrastracture.output;

import by.zemich.advertisementservice.application.ports.output.CategoryPersistenceOutputPort;
import by.zemich.advertisementservice.domain.entity.Category;
import by.zemich.advertisementservice.domain.exception.CategoryAttributeNotFoundException;
import by.zemich.advertisementservice.domain.exception.CategoryNotFoundException;
import by.zemich.advertisementservice.domain.valueobject.CategoryId;
import by.zemich.advertisementservice.domain.valueobject.Id;
import by.zemich.advertisementservice.infrastracture.output.repository.jpa.api.CategoryAttributeRepository;
import by.zemich.advertisementservice.infrastracture.output.repository.jpa.api.CategoryRepository;
import by.zemich.advertisementservice.infrastracture.output.repository.jpa.entity.CategoryAttributeEntity;
import by.zemich.advertisementservice.infrastracture.output.repository.jpa.entity.CategoryEntity;
import by.zemich.advertisementservice.infrastracture.output.repository.jpa.mapper.CategoryAttributeMapper;
import by.zemich.advertisementservice.infrastracture.output.repository.jpa.mapper.CategoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CategoryPersistenceOutputAdapter implements CategoryPersistenceOutputPort {

    private final CategoryRepository categoryRepository;
    private final CategoryAttributeRepository categoryAttributeRepository;

    @Override
    public Category getById(CategoryId id) {
        UUID uuid = id.uuid();
        return categoryRepository.findById(uuid)
                .map(entity -> {
                    Category category = CategoryMapper.mapToDomain(entity);
                    entity.getAttributes().stream().map(CategoryAttributeMapper::mapToDomain)
                            .forEach(category::addAttribute);
                    return category;
                })
                .orElseThrow(() -> new CategoryNotFoundException(uuid.toString()));
    }

    @Override
    public Category persist(Category createdCategory) {
        CategoryEntity categoryEntity = CategoryMapper.mapToEntity(createdCategory);
        createdCategory.getAttributes().stream()
                .map(attribute -> {
                    UUID categoryAttributeUuid = attribute.getId().uuid();
                    CategoryAttributeEntity categoryAttributeEntity = categoryAttributeRepository.findById(categoryAttributeUuid)
                            .orElseThrow(() -> new CategoryAttributeNotFoundException(categoryAttributeUuid.toString()));
                    return categoryAttributeEntity;
                }).forEach(categoryEntity::addAttribute);
        CategoryEntity savedCategory = categoryRepository.save(categoryEntity);
        return CategoryMapper.mapToDomain(savedCategory);
    }

    @Override
    public boolean existsById(Id categoryId) {
        return categoryRepository.existsById(categoryId.uuid());
    }

    @Override
    public boolean deleteById(CategoryId id) {
        return categoryRepository.findById(id.uuid())
                .map(entity -> {
                    categoryRepository.delete(entity);
                    return true;
                })
                .orElse(false);
    }

    @Override
    public List<Category> getAll() {
        return categoryRepository.findAll().stream()
                .map(
                        categoryEntity -> {
                            Category category = CategoryMapper.mapToDomain(categoryEntity);
                            categoryEntity.getAttributes().stream()
                                    .map(CategoryAttributeMapper::mapToDomain)
                                    .forEach(category::addAttribute);
                            return category;
                        }
                )
                .toList();
    }
}
