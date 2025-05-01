package by.zemich.device_catalog_service.service;

import by.zemich.device_catalog_service.domen.entities.BrandEntity;
import by.zemich.device_catalog_service.repository.BrandJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BrandService {

    private final BrandJpaRepository brandRepository;

    @Transactional(readOnly = true)
    public Optional<BrandEntity> getByUuid(UUID uuid) {
        return brandRepository.findById(uuid);
    }

    @Transactional(readOnly = true)
    public Optional<BrandEntity> getByName(String name) {
        return brandRepository.findByName(name);
    }

    @Transactional(readOnly = true)
    public boolean existsByName(String name) {
        return brandRepository.existsByName(name);
    }

    @Transactional(readOnly = true)
    public List<BrandEntity> getAll() {
        return brandRepository.findAll();
    }

    @Transactional
    public BrandEntity save(BrandEntity brand) {
        return brandRepository.save(brand);
    }

    public void delete(BrandEntity brand) {
        brandRepository.delete(brand);
    }
}
