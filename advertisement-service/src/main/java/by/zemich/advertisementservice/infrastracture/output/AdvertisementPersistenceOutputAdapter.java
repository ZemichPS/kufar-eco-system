package by.zemich.advertisementservice.infrastracture.output;

import by.zemich.advertisementservice.application.ports.output.AdvertisementPerststenceOutputPort;
import by.zemich.advertisementservice.domain.entity.Advertisement;
import by.zemich.advertisementservice.domain.exception.AdvertisementNotFoundException;
import by.zemich.advertisementservice.domain.exception.CategoryAttributeNotFoundException;
import by.zemich.advertisementservice.domain.exception.CategoryNotFoundException;
import by.zemich.advertisementservice.domain.valueobject.AdvertisementId;
import by.zemich.advertisementservice.domain.valueobject.UserId;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
@CacheConfig(
        cacheManager = "advertisementCaffeineCacheManager",
        cacheNames = "advertisementsForCommandCache"
)
public class AdvertisementPersistenceOutputAdapter implements AdvertisementPerststenceOutputPort {

    private final AdvertisementRepository advertisementRepository;
    private final CategoryRepository categoryRepository;
    private final CategoryAttributeRepository categoryAttributeRepository;
    private final CacheManager cacheManager;

    @Override
    @CachePut(key = "#advertisement.id.uuid()")
    @CacheEvict(cacheNames = "advertisementsForQueryCache", allEntries = true)
    public Advertisement saveNew(Advertisement advertisement) {
        AdvertisementEntity advertisementEntity = AdvertisementMapper.mapToEntity(advertisement);
        UUID categoryId = advertisement.getCategoryId().uuid();
        CategoryEntity categoryEntity = categoryRepository
                .findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(categoryId.toString()));
        advertisementEntity.setCategory(categoryEntity);
        advertisementRepository.save(advertisementEntity);
        return mapToDomain(advertisementEntity);
    }

    @Override
    @CachePut(key = "#advertisement.id.uuid()")
    @CacheEvict(cacheNames = "advertisementsForQueryCache", allEntries = true)
    public Advertisement update(Advertisement advertisement) {
        UUID advertisementId = advertisement.getId().uuid();
        UUID categoryUuid = advertisement.getCategoryId().uuid();

        if (!advertisementRepository.existsById(advertisementId)) {
            throw new AdvertisementNotFoundException(advertisementId.toString());
        }

        CategoryEntity categoryEntity = categoryRepository.findById(categoryUuid)
                .orElseThrow(() -> new CategoryNotFoundException(categoryUuid.toString()));

        AdvertisementEntity advertisementEntity = AdvertisementMapper.mapToEntity(advertisement);
        advertisementEntity.setCategory(categoryEntity);

        advertisement.getAttributes().stream()
                .map(attributeDomain -> {
                    AdvertisementAttributeEntity advertisementAttributeEntity = AdvertisementAttributeMapper.mapToEntity(attributeDomain);
                    UUID categoryAttributeUuid = attributeDomain.getCategoryAttributeId().uuid();
                    CategoryAttributeEntity categoryAttributeEntity = categoryAttributeRepository.findById(categoryAttributeUuid).orElseThrow(() -> new CategoryAttributeNotFoundException(categoryAttributeUuid.toString()));
                    advertisementAttributeEntity.setCategoryAttribute(categoryAttributeEntity);
                    return advertisementAttributeEntity;
                })
                .forEach(advertisementEntity::addAttribute);
        advertisementRepository.save(advertisementEntity);
        return mapToDomain(advertisementEntity);
    }

    @Override
    public void delete(AdvertisementId id) {

        Cache advertisementsForCommandCache = cacheManager.getCache("advertisementsForCommandCache");
        if (advertisementsForCommandCache != null) {
            advertisementsForCommandCache.evict(id.uuid());
        } else {
            log.warn("Cache 'advertisementsForCommand' not found. Skipping cache eviction.");
        }

        Cache advertisementsForQueryCache = cacheManager.getCache("advertisementsForQueryCache");
        if (advertisementsForQueryCache != null) {
            advertisementsForQueryCache.clear();
        } else {
            log.warn("Cache 'advertisementsForQuery' not found. Skipping cache clear.");
        }

        UUID advertisementId = id.uuid();

        if (!advertisementRepository.existsById(advertisementId)) {
            throw new AdvertisementNotFoundException(advertisementId.toString());
        }
        advertisementRepository.deleteById(advertisementId);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(key = "#id.uuid()")
    public Optional<Advertisement> retrieveById(AdvertisementId id) {
        UUID advertisementId = id.uuid();
        return advertisementRepository.findById(advertisementId)
                .map(this::mapToDomain);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(key = "#id.uuid()")
    public boolean existsById(AdvertisementId id) {
        return advertisementRepository.existsById(id.uuid());
    }

    @Override
    @Cacheable(key = "#userId.uuid()")
    public List<Advertisement> retrieveAllByUserId(UserId userId) {
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
