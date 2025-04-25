package by.zemich.advertisementservice.infrastracture.output;

import by.zemich.advertisementservice.application.ports.output.AdvertisementPerststenceOutputPort;
import by.zemich.advertisementservice.domain.entity.Advertisement;
import by.zemich.advertisementservice.domain.exception.AdvertisementNotFoundException;
import by.zemich.advertisementservice.domain.exception.CategoryAttributeNotFoundException;
import by.zemich.advertisementservice.domain.exception.CategoryNotFoundException;
import by.zemich.advertisementservice.domain.valueobject.AdvertisementId;
import by.zemich.advertisementservice.domain.valueobject.Id;
import by.zemich.advertisementservice.infrastracture.output.repository.jpa.api.AdvertisementAttributeRepository;
import by.zemich.advertisementservice.infrastracture.output.repository.jpa.api.AdvertisementRepository;
import by.zemich.advertisementservice.infrastracture.output.repository.jpa.api.CategoryAttributeRepository;
import by.zemich.advertisementservice.infrastracture.output.repository.jpa.api.CategoryRepository;
import by.zemich.advertisementservice.infrastracture.output.repository.jpa.entity.AdvertisementAttributeEntity;
import by.zemich.advertisementservice.infrastracture.output.repository.jpa.entity.AdvertisementEntity;
import by.zemich.advertisementservice.infrastracture.output.repository.jpa.entity.CategoryAttributeEntity;
import by.zemich.advertisementservice.infrastracture.output.repository.jpa.entity.CategoryEntity;
import by.zemich.advertisementservice.infrastracture.output.repository.jpa.mapper.AdvertisementAttributeMapper;
import by.zemich.advertisementservice.infrastracture.output.repository.jpa.mapper.AdvertisementMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AdvertisementPerststenceOutputAdapter implements AdvertisementPerststenceOutputPort {

    private final AdvertisementRepository advertisementRepository;
    private final AdvertisementAttributeRepository advertisementAttributeRepository;
    private final CategoryRepository categoryRepository;
    private final CategoryAttributeRepository categoryAttributeRepository;

    @Override
    public Advertisement saveNew(Advertisement advertisement) {
        AdvertisementEntity advertisementEntity = AdvertisementMapper.mapToEntity(advertisement);
        UUID categoryId = advertisement.getCategoryId().uuid();
        CategoryEntity categoryEntity = categoryRepository.findById(categoryId).orElseThrow(() -> new CategoryNotFoundException(categoryId.toString()));
        advertisementEntity.setCategory(categoryEntity);
        advertisementRepository.save(advertisementEntity);
        return mapToDomain(advertisementEntity);
    }

    @Override
    public void update(Advertisement advertisement) {
        UUID advertisementId = advertisement.getId().uuid();
        AdvertisementEntity advertisementEntity = advertisementRepository.findById(advertisementId).orElseThrow(() -> new AdvertisementNotFoundException(advertisementId.toString()));
        advertisement.getAttributes().stream()
                .map(attribute -> {
                    AdvertisementAttributeEntity advertisementAttributeEntity = advertisementAttributeRepository.findById(attribute.getId().uuid()).orElseGet(() ->
                    {
                        AdvertisementAttributeEntity entity = AdvertisementAttributeMapper.mapToEntity(attribute);
                        UUID categoryAttributeId = attribute.getCategoryAttributeId().uuid();
                        CategoryAttributeEntity categoryAttributeEntity = categoryAttributeRepository.findById(categoryAttributeId)
                                .orElseThrow(() -> new CategoryAttributeNotFoundException(categoryAttributeId.toString()));
                        entity.setCategoryAttribute(categoryAttributeEntity);
                        return entity;
                    });
                    if (!advertisementAttributeEntity.getValue().equalsIgnoreCase(attribute.getValue()))
                        advertisementAttributeEntity.setValue(attribute.getValue());
                    return advertisementAttributeEntity;
                }).
                forEach(advertisementEntity::addAttribute);

        UUID categoryId = advertisement.getCategoryId().uuid();
        CategoryEntity categoryEntity = categoryRepository.findById(categoryId).orElseThrow(() -> new CategoryNotFoundException(categoryId.toString()));
        advertisementEntity.setCategory(categoryEntity);
        advertisementRepository.save(advertisementEntity);
    }

    @Override
    public void delete(AdvertisementId id) {

    }

    @Override
    public Optional<Advertisement> retrieveById(AdvertisementId id) {
        UUID advertisementId = id.uuid();
        return advertisementRepository.findById(advertisementId)
                .map(this::mapToDomain);
    }

    @Override
    public boolean existsById(AdvertisementId id) {
        return advertisementRepository.existsById(id.uuid());
    }

    @Override
    public List<Advertisement> retrieveAllByUserId(Id userId) {
        return List.of();
    }


    public Advertisement mapToDomain(AdvertisementEntity advertisementEntity) {
        Advertisement advertisement = AdvertisementMapper.mapToDomain(advertisementEntity);
        advertisementEntity.getAttributes().stream()
                .map(AdvertisementAttributeMapper::mapToDomain)
                .forEach(advertisement::addAttribute);
        return advertisement;
    }
}
