package by.zemich.advertisementservice.infrastracture.output;

import by.zemich.advertisementservice.application.ports.output.CategoryPersistenceOutputPort;
import by.zemich.advertisementservice.domain.entity.Category;
import by.zemich.advertisementservice.domain.valueobject.CategoryAttribute;
import by.zemich.advertisementservice.domain.valueobject.Id;
import by.zemich.advertisementservice.infrastracture.output.repository.jpa.api.CategoryRepository;
import by.zemich.advertisementservice.infrastracture.output.repository.jpa.entity.CategoryAttributeEntity;
import by.zemich.advertisementservice.infrastracture.output.repository.jpa.entity.CategoryEntity;
import by.zemich.advertisementservice.infrastracture.output.repository.jpa.mapper.CategoryAttributeMapper;
import by.zemich.advertisementservice.infrastracture.output.repository.jpa.mapper.CategoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CategoryPersistenceOutputAdapter implements CategoryPersistenceOutputPort {

    private final CategoryRepository categoryRepository;

    @Override
    public Optional<Category> getById(Id id) {
        UUID uuid = id.uuid();

        return categoryRepository.findById(uuid)
                .map( entity -> {
                    Category category = CategoryMapper.mapToDomain(entity);
                    entity.getAttributes().stream().map(CategoryAttributeMapper::mapToDomain)
                            .forEach(category::addAttribute);
                    return category;
                })
                .or(Optional::empty);

    }

    @Override
    public Category persist(Category createdCategory) {
        CategoryEntity categoryEntity = CategoryMapper.mapToEntity(createdCategory);
        createdCategory.getAttributes().stream()
                .map(CategoryAttributeMapper::mapToEntity)
                .forEach(categoryEntity::addAttribute);
        CategoryEntity savedCategory = categoryRepository.save(categoryEntity);
        return CategoryMapper.mapToDomain(savedCategory);
    }

    @Override
    public void persist(Id categoryId, CategoryAttribute attribute) {
        UUID categoryUuid = categoryId.uuid();
        CategoryEntity categoryEntity = categoryRepository.findById(categoryUuid).orElseThrow();
        CategoryAttributeEntity categoryAttributeEntity = CategoryAttributeMapper.mapToEntity(attribute);
        categoryEntity.addAttribute(categoryAttributeEntity);
        categoryRepository.save(categoryEntity);
    }

    @Override
    public boolean existsById(Id categoryId) {
        return categoryRepository.existsById(categoryId.uuid());
    }

    @Override
    public boolean deleteById(Id id) {
        return categoryRepository.findById(id.uuid())
                .map(entity -> {
                    categoryRepository.delete(entity);
                    return true;
                })
                .orElse(false);
    }
}
