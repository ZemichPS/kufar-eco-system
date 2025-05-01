package by.zemich.geo_service.service;

import by.zemich.geo_service.domain.dtos.GeoDataDto;
import by.zemich.geo_service.domain.entities.GeoDataEntity;
import by.zemich.geo_service.utils.GeoDataMapper;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
@CacheConfig(
        cacheManager = "caffeineCacheManager",
        cacheNames = "geoDataDtoCache"
)
public class GeoFacadeService {

    private final GeoDataService geoDataService;

    @CacheEvict(allEntries = true)
    @CachePut(key = "#geoDataDto.id")
    public void saveOrUpdate(GeoDataDto geoDataDto) {
        GeoDataEntity geoDataEntity = geoDataService.getById(geoDataDto.getId())
                .map(entity -> {
                    entity.setName(geoDataDto.getName());
                    entity.setArea(geoDataDto.getArea());
                    entity.setTag(geoDataDto.getTag());
                    entity.setPid(geoDataDto.getPid());
                    entity.setRegion(geoDataDto.getRegion());
                    entity.setType(geoDataDto.getType());
                    return entity;
                })
                .orElseGet(() -> GeoDataMapper.map(geoDataDto));
        geoDataService.save(geoDataEntity);
    }

    @Cacheable(key = "'all'")
    public List<GeoDataDto> getAll() {
        return geoDataService.getAll().stream()
                .map(GeoDataMapper::map)
                .toList();
    }

    @Cacheable(key = "'areas'")
    public List<GeoDataDto> getAreas() {
        return geoDataService.getAll().stream()
                .filter(entity -> entity.getType().equals("area"))
                .map(GeoDataMapper::map)
                .toList();
    }

    @Cacheable(key = "'cities'")
    public List<GeoDataDto> getCities() {
        return geoDataService.getAll().stream()
                .filter(entity -> entity.getType().equals("city"))
                .map(GeoDataMapper::map)
                .toList();
    }

    @Cacheable(key = "'setlements'")
    public List<GeoDataDto> getUrbanSettlements() {
        return geoDataService.getAll().stream()
                .filter(entity -> entity.getType().equals("urban_settlement"))
                .map(GeoDataMapper::map)
                .toList();
    }


    @Cacheable(key = "#id")
    public GeoDataDto getGeoDataById(Integer id) {
        return geoDataService.getById(id)
                .map(GeoDataMapper::map)
                .orElseThrow();
    }

    @Cacheable(key = "'citiesByregions_' + #region" )
    public List<GeoDataDto> getCitiesByRegion(Integer region) {
        return geoDataService.getAll().stream()
                .filter(entity -> Objects.nonNull(entity.getRegion()))
                .filter(entity -> entity.getRegion().equals(region))
                .filter(entity -> entity.getType().equals("city"))
                .map(GeoDataMapper::map)
                .toList();
    }
}
