package by.zemich.device_catalog_service.infrastructure.scheduled;

import by.zemich.device_catalog_service.domen.entities.BrandEntity;
import by.zemich.device_catalog_service.service.BrandService;
import by.zemich.device_catalog_service.service.DataProvider;
import by.zemich.device_catalog_service.utils.BrandMapper;
import by.zemich.device_catalog_service.utils.ModelMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@EnableScheduling
@RequiredArgsConstructor
public class ScheduledDataUpdater {

    private final DataProvider dataProvider;
    private final BrandService brandService;

    @Scheduled(cron = "* * * * * *") //
    @CacheEvict(cacheNames = "brandsCache", allEntries = true)
    public void updateData() {
        dataProvider.getData().stream()
                .map(brandDto -> {
                    BrandEntity entity = BrandMapper.map(brandDto);
                    brandDto.getModels().stream()
                            .map(ModelMapper::map)
                            .forEach(entity.getModels()::add);
                    return entity;
                })
                .forEach(brandService::save);
    }


}
