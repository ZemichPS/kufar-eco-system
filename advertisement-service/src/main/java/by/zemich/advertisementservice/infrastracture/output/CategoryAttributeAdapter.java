package by.zemich.advertisementservice.infrastracture.output;

import by.zemich.advertisementservice.application.ports.output.CategoryAttributeOutputPort;
import by.zemich.advertisementservice.domain.exception.DuplicateEntityException;
import by.zemich.advertisementservice.domain.exception.EntityNotFoundException;
import by.zemich.advertisementservice.domain.valueobject.CategoryAttribute;
import by.zemich.advertisementservice.domain.valueobject.Id;
import by.zemich.advertisementservice.infrastracture.output.repository.jpa.api.CategoryAttributeRepository;
import by.zemich.advertisementservice.infrastracture.output.repository.jpa.api.CategoryRepository;
import by.zemich.advertisementservice.infrastracture.output.repository.jpa.entity.CategoryAttributeEntity;
import by.zemich.advertisementservice.infrastracture.output.repository.jpa.entity.CategoryEntity;
import by.zemich.advertisementservice.infrastracture.output.repository.jpa.mapper.CategoryAttributeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CategoryAttributeAdapter implements CategoryAttributeOutputPort {

    private final CategoryRepository categoryRepository;
    private final CategoryAttributeRepository categoryAttributeRepository;

    @Override
    public void persist(Id categoryId, CategoryAttribute categoryAttribute) {
        UUID categoryUuid = categoryId.uuid();
        CategoryEntity category = categoryRepository.findById(categoryUuid).orElseThrow(() -> new EntityNotFoundException("Category not found"));

//        if (categoryAttributeRepository.existsByNameAndCategory(categoryAttribute.name(), category)) {
//            throw new DuplicateEntityException("Attribute " + categoryAttribute.name() + " already exists in category " + categoryId.uuid());
//        }
        CategoryAttributeEntity categoryAttributeEntity = CategoryAttributeMapper.mapToEntity(categoryAttribute);
     //   categoryAttributeEntity.setCategory(category);
        categoryAttributeRepository.save(categoryAttributeEntity);
    }

    @Override
    public boolean existsById(Id categoryAttributeId) {
        return categoryAttributeRepository.existsById(categoryAttributeId.uuid());
    }

    @Override
    public boolean delete(Id categoryAttributeId) {
        if (!categoryAttributeRepository.existsById(categoryAttributeId.uuid())) {
            return false;
        }
        categoryAttributeRepository.deleteById(categoryAttributeId.uuid());
        return true;
    }
}
