package by.zemich.parser.application.service;

import by.zemich.parser.domain.model.Manufacturer;
import by.zemich.parser.domain.model.Model;
import by.zemich.parser.infrastructure.repository.jparepository.ManufacturerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ManufactureService {
    private final ManufacturerRepository manufacturerRepository;

    @Transactional(readOnly = true)
    public List<Manufacturer> getAllManufacturers() {
        return manufacturerRepository.findAll();
    }

    @Transactional()
    public List<Model> getAllModelsByManufacturer(String manufacturerName) {
        Manufacturer manufacturer = manufacturerRepository.findByName(manufacturerName).orElseThrow();
        return manufacturer.getModels().stream().toList();
    }

    @Transactional(readOnly = true)
    public List<Model> getAllModelsByManufacturer(Long manufacturerId) {
        Manufacturer manufacturer = manufacturerRepository.findById(manufacturerId).orElseThrow();
        return manufacturer.getModels();
    }

    public boolean existsById(Long id) {
        return manufacturerRepository.existsById(id);
    }

    public Optional<Manufacturer> getById(Long id) {
        return manufacturerRepository.findById(id);
    }

    public Optional<Manufacturer> getByName(String name) {
        return manufacturerRepository.findByName(name);
    }

    public Manufacturer save(Manufacturer manufacturer) {
        return manufacturerRepository.save(manufacturer);
    }



}
