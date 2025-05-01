package by.zemich.geo_service.service;

import by.zemich.geo_service.domain.entities.GeoDataEntity;
import by.zemich.geo_service.repositories.GeoDataRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class GeoDataService {

    private final GeoDataRepository geoDataRepository;

    @Transactional(readOnly = true)
    public List<GeoDataEntity> getAll() {
        return geoDataRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<GeoDataEntity> getById(int id) {
        return geoDataRepository.findById(id);
    }

    public GeoDataEntity save(GeoDataEntity geoDataEntity) {
        return geoDataRepository.save(geoDataEntity);
    }

}
