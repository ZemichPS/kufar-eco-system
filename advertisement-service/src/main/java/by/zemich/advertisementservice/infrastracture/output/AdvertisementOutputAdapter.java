package by.zemich.advertisementservice.infrastracture.output;

import by.zemich.advertisementservice.application.ports.output.AdvertisementOutputPort;
import by.zemich.advertisementservice.domain.entity.Advertisement;
import by.zemich.advertisementservice.domain.entity.AdvertisementAttribute;
import by.zemich.advertisementservice.domain.entity.Category;
import by.zemich.advertisementservice.domain.exception.AdvertisementNotFoundException;
import by.zemich.advertisementservice.domain.exception.CategoryAttributeNotFoundException;
import by.zemich.advertisementservice.domain.exception.CategoryNotFoundException;
import by.zemich.advertisementservice.domain.request.Pagination;
import by.zemich.advertisementservice.domain.valueobject.Id;
import by.zemich.advertisementservice.domain.valueobject.Side;
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
import by.zemich.advertisementservice.infrastracture.output.repository.jpa.mapper.CategoryAttributeMapper;
import by.zemich.advertisementservice.infrastracture.output.repository.jpa.mapper.CategoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AdvertisementOutputAdapter implements AdvertisementOutputPort {

    private final AdvertisementRepository advertisementRepository;
    private final AdvertisementAttributeRepository advertisementAttributeRepository;
    private final CategoryRepository categoryRepository;
    private final CategoryAttributeRepository categoryAttributeRepository;

    @Override
    public void saveNew(Advertisement advertisement) {
        AdvertisementEntity advertisementEntity = AdvertisementMapper.mapToEntity(advertisement);
        advertisement.getAttributes().stream()
                .map(advertisementAttribute -> {
                    AdvertisementAttributeEntity attributeEntity = AdvertisementAttributeMapper.mapToEntity(advertisementAttribute);
                    UUID categoryAttributeId = attributeEntity.getCategoryAttribute().getUuid();
                    CategoryAttributeEntity categoryAttributeEntity = categoryAttributeRepository.findById(categoryAttributeId)
                            .orElseThrow(() -> new CategoryAttributeNotFoundException(categoryAttributeId.toString()));
                    attributeEntity.setCategoryAttribute(categoryAttributeEntity);
                    return attributeEntity;
                })
                .forEach(advertisementEntity::addAttribute);
        UUID categoryId = advertisement.getCategory().getId().uuid();
        CategoryEntity categoryEntity = categoryRepository.findById(categoryId).orElseThrow(() -> new CategoryNotFoundException(categoryId.toString()));
        advertisementEntity.setCategory(categoryEntity);
        advertisementRepository.save(advertisementEntity);
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
                        UUID categoryAttributeId = attribute.getCategoryAttribute().getId().uuid();
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

        UUID categoryId = advertisement.getCategory().getId().uuid();
        CategoryEntity categoryEntity = categoryRepository.findById(categoryId).orElseThrow(() -> new CategoryNotFoundException(categoryId.toString()));
        advertisementEntity.setCategory(categoryEntity);
        advertisementRepository.save(advertisementEntity);
    }

    @Override
    public void updatePrice(Advertisement advertisement) {

    }

    @Override
    public Advertisement retrieveById(Id id) {
        UUID advertisementId = id.uuid();
        AdvertisementEntity advertisementEntity = advertisementRepository.findById(advertisementId)
                .orElseThrow(() -> new AdvertisementNotFoundException(advertisementId.toString()));
        return mapToDomain(advertisementEntity);
    }


    @Override
    public List<Advertisement> retrieveAll(Pagination pagination) {
        Sort.Direction sortDirection = pagination.isAsc() ? Sort.Direction.ASC : Sort.Direction.DESC;
        boolean active = pagination.isOnlyActive();
        Pageable pageable = PageRequest.of(
                pagination.getPage(),
                pagination.getSize(),
                Sort.by(sortDirection, pagination.getSortBy())
        );
        return advertisementRepository.findAllWithEagerRelationshipsByActiveIs(pageable, active).stream()
                .map(this::mapToDomain)
                .toList();
    }

    @Override
    public List<Advertisement> retrieveAllByUserId(Id userId) {
        UUID userUuid = userId.uuid();
        return advertisementRepository.findAllByUserUuid(userUuid).stream()
                .map(AdvertisementMapper::mapToDomain)
                .toList();
    }

    @Override
    public List<Advertisement> retrieveByAttributes(List<AdvertisementAttribute> attributes, Side side) {
        List<UUID> categoryAttributeIds = attributes.stream()
                .map(attribute -> attribute.getCategoryAttribute().getId().uuid())
                .toList();

        List<String> values = attributes.stream().map(AdvertisementAttribute::getValue).toList();
        String paramSide = side.name();

        return advertisementRepository.findByAttributesAndSide(categoryAttributeIds, values, paramSide).stream()
                .map(this::mapToDomain)
                .toList();
    }

    public Advertisement mapToDomain(AdvertisementEntity advertisementEntity) {
        CategoryEntity categoryEntity = advertisementEntity.getCategory();
        Advertisement advertisement = AdvertisementMapper.mapToDomain(advertisementEntity);
        Category category = CategoryMapper.mapToDomain(categoryEntity);
        advertisementEntity.getAttributes().stream()
                .map(AdvertisementAttributeMapper::mapToDomain)
                .forEach(advertisement::addAttribute);
        advertisement.addCategory(category);
        return advertisement;
    }
}
