package by.zemich.device_catalog_service.service;

import by.zemich.device_catalog_service.domen.entities.Model;
import by.zemich.device_catalog_service.repository.ModelJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ModelService {

    private final ModelJpaRepository modelRepository;

    public boolean existById(UUID id) {
        return modelRepository.existsById(id);
    }

    public boolean existByName(String name) {
        return modelRepository.existsByName(name);
    }

    public List<Model> findAll() {
        return modelRepository.findAll();
    }

    public Optional<Model> findById(UUID uuid) {
        return modelRepository.findById(uuid);
    }

    public Optional<Model> findByName(String name) {
        return modelRepository.findByName(name);
    }

}
