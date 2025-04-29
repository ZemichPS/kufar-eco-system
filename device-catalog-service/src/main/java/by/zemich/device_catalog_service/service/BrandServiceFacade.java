package by.zemich.device_catalog_service.service;

import by.zemich.device_catalog_service.domen.dto.BrandDto;
import by.zemich.device_catalog_service.domen.entities.Brand;
import by.zemich.device_catalog_service.utils.BrandMapper;
import by.zemich.device_catalog_service.utils.ModelMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BrandServiceFacade {

    private final BrandService brandService;

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



}
