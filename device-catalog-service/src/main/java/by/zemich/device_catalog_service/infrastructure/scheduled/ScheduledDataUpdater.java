package by.zemich.device_catalog_service.infrastructure.scheduled;

import by.zemich.device_catalog_service.domen.dtos.ModelDto;
import by.zemich.device_catalog_service.domen.entities.BrandEntity;
import by.zemich.device_catalog_service.domen.entities.ModelEntity;
import by.zemich.device_catalog_service.service.BrandService;
import by.zemich.device_catalog_service.service.DataProvider;
import by.zemich.device_catalog_service.service.ModelService;
import by.zemich.device_catalog_service.utils.BrandMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
//@EnableScheduling
@RequiredArgsConstructor
public class ScheduledDataUpdater {

    private final DataProvider dataProvider;
    private final BrandService brandService;
    private final ModelService modelService;

    @Scheduled(cron = "* * 1 * * *") //
    @CacheEvict(cacheNames = "brandsCache", allEntries = true)
    public void updateData() {
        dataProvider.getData().stream()

                .map(brandDto -> {

                    BrandEntity entity = brandService.getByName(brandDto.getName())
                            .orElse(BrandMapper.map(brandDto));

                    brandDto.getModels().stream()
                            .map(ModelDto::getName)
                            .filter(modelName -> !modelService.existByName(modelName))
                            .map(ModelEntity::new)
                            .forEach(entity.getModelEntities()::add);
                    return entity;
                })
                .forEach(brandService::save);
    }


}
