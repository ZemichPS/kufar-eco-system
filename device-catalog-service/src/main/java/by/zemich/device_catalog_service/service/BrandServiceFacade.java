package by.zemich.device_catalog_service.service;

import by.zemich.device_catalog_service.domen.dto.BrandDto;
import by.zemich.device_catalog_service.domen.dto.ModelDto;
import by.zemich.device_catalog_service.domen.entities.BrandEntity;
import by.zemich.device_catalog_service.domen.entities.Model;
import by.zemich.device_catalog_service.utils.BrandMapper;
import by.zemich.device_catalog_service.utils.ModelMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BrandServiceFacade {

    private final BrandService brandService;
    private final ModelService modelService;

    public List<BrandDto> getAllBrands() {
        return brandService.getAll().stream()
                .map(entity -> {
                    BrandDto dto = BrandMapper.map(entity);
                    entity.getModels().stream()
                            .map(ModelMapper::map)
                            .forEach(dto.getModels()::add);
                    return dto;
                })
                .toList();
    }

    public void saveBrand(BrandDto brandDto) {
        BrandEntity entity = brandService.getByName(brandDto.getName())
                .orElse(BrandMapper.map(brandDto));

        brandDto.getModels().stream()
                .map(ModelDto::getName)
                .filter(modelName -> !modelService.existByName(modelName))
                .map(modelName -> new Model(UUID.randomUUID(), modelName))
                .forEach(entity::addModel);
        brandService.save(entity);
    }


}
