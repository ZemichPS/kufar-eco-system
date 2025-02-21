package by.zemich.advertisementservice.infrastracture.output;

import by.zemich.advertisementservice.application.ports.output.CategoryAttributeOutputPort;
import by.zemich.advertisementservice.domain.exception.CategoryAttributeNotFoundException;
import by.zemich.advertisementservice.domain.valueobject.CategoryAttribute;
import by.zemich.advertisementservice.domain.valueobject.Id;
import by.zemich.advertisementservice.infrastracture.output.repository.jpa.api.CategoryAttributeRepository;
import by.zemich.advertisementservice.infrastracture.output.repository.jpa.entity.CategoryAttributeEntity;
import by.zemich.advertisementservice.infrastracture.output.repository.jpa.mapper.CategoryAttributeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CategoryAttributeAdapter implements CategoryAttributeOutputPort {

    private final CategoryAttributeRepository categoryAttributeRepository;

    @Override
    public void persist(CategoryAttribute categoryAttribute) {
        CategoryAttributeEntity categoryAttributeEntity = CategoryAttributeMapper.mapToEntity(categoryAttribute);
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

    @Override
    public List<CategoryAttribute> getAll() {
        return categoryAttributeRepository.findAll().stream()
                .map(CategoryAttributeMapper::mapToDomain)
                .toList();
    }

    @Override
    public CategoryAttribute getById(Id categoryAttributeId) {
        UUID categoryAttributeUuid = categoryAttributeId.uuid();
        return categoryAttributeRepository.findById(categoryAttributeUuid)
                .map(CategoryAttributeMapper::mapToDomain)
                .orElseThrow(()-> new CategoryAttributeNotFoundException(categoryAttributeUuid.toString()));
    }

}
