package by.zemich.device_catalog_service.service;

import by.zemich.device_catalog_service.domen.exceptions.BusinessException;
import by.zemich.device_catalog_service.domen.dtos.BrandDto;
import by.zemich.device_catalog_service.domen.dtos.BrandModifyDto;
import by.zemich.device_catalog_service.domen.dtos.ModelCreateDto;
import by.zemich.device_catalog_service.domen.dtos.ModelDto;
import by.zemich.device_catalog_service.domen.entities.BrandEntity;
import by.zemich.device_catalog_service.domen.entities.ModelEntity;
import by.zemich.device_catalog_service.utils.BrandMapper;
import by.zemich.device_catalog_service.utils.ModelMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@CacheConfig(
        cacheManager = "caffeineCacheManager",
        cacheNames = "brandsDtoCache"
)
public class BrandServiceFacade {

    private final BrandService brandService;
    private final ModelService modelService;

    @Cacheable(key = "'all'")
    public List<BrandDto> getAllBrands() {
        return brandService.getAll().stream()
                .map(entity -> {
                    BrandDto dto = BrandMapper.map(entity);
                    entity.getModelEntities().stream()
                            .map(ModelMapper::map)
                            .forEach(dto.getModels()::add);
                    return dto;
                })
                .toList();
    }

    @Cacheable(key = "#brandName")
    public BrandDto getByName(String brandName) {
        return brandService.getByName(brandName)
                .map(this::mapToDto)
                .orElseThrow();
    }

    @Cacheable(key = "#uuid")
    public BrandDto getByUuid(UUID uuid) {
        return brandService.getByUuid(uuid)
                .map(this::mapToDto)
                .orElseThrow();
    }

    @Caching(
            evict = {
                    @CacheEvict(key = "'all'"),
            },
            put = {
                    @CachePut(key = "#brandDto.name"),
                    @CachePut(key = "#brandDto.uuid")
            })
    public BrandDto saveOrUpdateBrand(BrandDto brandDto) {
        BrandEntity entity = brandService.getByName(brandDto.getName())
                .orElse(BrandMapper.map(brandDto));
        brandDto.getModels().stream()
                .map(ModelDto::getName)
                .filter(modelName -> !modelService.existByName(modelName))
                .map(modelName -> new ModelEntity(UUID.randomUUID(), modelName))
                .forEach(entity::addModel);
        BrandEntity saved = brandService.save(entity);
        return mapToDto(saved);
    }


    @Caching(
            evict = {
                    @CacheEvict(key = "'all'")
            },
            put = {
                    @CachePut(key = "#result.name"),
                    @CachePut(key = "#result.uuid")
            })
    public BrandDto addModel(UUID brandUuid, ModelCreateDto createDto) {
        BrandEntity brandEntity = brandService.getByUuid(brandUuid)
                .orElseThrow();
        String modelName = createDto.getModelName();
        if (modelService.existByName(modelName)) throw new BusinessException("Model " + modelName + " already exists");
        ModelEntity modelEntity = ModelEntity.builder()
                .uuid(UUID.randomUUID())
                .name(modelName)
                .build();
        brandEntity.addModel(modelEntity);
        BrandEntity savedBrandEntity = brandService.save(brandEntity);
        return mapToDto(savedBrandEntity);
    }

    @Caching(
            evict = {
                    @CacheEvict(key = "'all'")
            },
            put = {
                    @CachePut(key = "#result.name"),
                    @CachePut(key = "#result.uuid")
            })
    public BrandDto create(BrandModifyDto brandModifyDto) {
        String brandName = brandModifyDto.getBrandName();
        if (brandService.existsByName(brandName)) throw new BusinessException("Brand " + brandName + " already exists");
        BrandDto brandDto = BrandDto.builder()
                .uuid(UUID.randomUUID())
                .name(brandName)
                .models(new ArrayList<>())
                .build();
        BrandEntity newBrandEntity = BrandMapper.map(brandDto);
        brandService.save(newBrandEntity);
        return mapToDto(newBrandEntity);
    }

    @Caching(
            evict = {
                    @CacheEvict(key = "'all'")
            },
            put = {
                    @CachePut(key = "#brandUuid"),
                    @CachePut(key = "#updateDto.brandName")
            })
    public void update(UUID brandUuid, BrandModifyDto updateDto) {
        BrandEntity brandEntity = brandService.getByUuid(brandUuid)
                .map(entity -> {
                    entity.setName(updateDto.getBrandName());
                    return entity;
                })
                .orElseThrow();
        brandService.save(brandEntity);
    }

    @Caching(
            evict = {
                    @CacheEvict(key = "'all'"),
                    @CacheEvict(key = "#result.uuid"),
                    @CacheEvict(key = "#result.name"),
            }
    )
    public BrandEntity deleteByUuid(UUID branduuid) {
        BrandEntity entityForDelete = brandService.getByUuid(branduuid).orElseThrow();
        brandService.delete(entityForDelete);
        return entityForDelete;
    }

    private BrandDto mapToDto(BrandEntity brandEntity) {
        BrandDto dto = BrandMapper.map(brandEntity);
        brandEntity.getModelEntities().stream()
                .map(ModelMapper::map)
                .forEach(dto.getModels()::add);
        return dto;
    }
}
