package by.zemich.parser.application.service;

import by.zemich.parser.domain.model.Advertisement;
import by.zemich.parser.infrastructure.repository.jparepository.AdvertisementRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@CacheConfig(
        cacheManager =  "advertisementServiceCaffeineCacheManager",
        cacheNames = "advertisements"
)
public class AdvertisementService {
    private final AdvertisementRepository adsRepository;
    private final ObjectMapper jsonMapper;

    @CachePut(key = "#advertisement.id")
    public Advertisement save(Advertisement advertisement) {
        return adsRepository.save(advertisement);
    }

    @Cacheable(
            key = "'pageable-' + #pageable.pageNumber + '-' + #pageable.pageSize"
    )
    public Page<Advertisement> get(Pageable pageable) {
        return adsRepository.findAll(pageable);
    }

    public Advertisement getByLink(String link) {
        return adsRepository.findByLink(link);
    }

    @Cacheable
    public List<Advertisement> getAll() {
        return adsRepository.findAll();
    }

    @Cacheable(
            key = "'brand-' + #brand + '-model-' + #model"
    )
    @Transactional(readOnly = true)
    public List<Advertisement> getAllByBrandAndModel(String brand, String model) {
        String parameters = createParameters(
                List.of(new Advertisement.Parameter("phones_brand", brand),
                        new Advertisement.Parameter("phones_model", model))
        );
        return adsRepository.findAllByParameters(parameters);
    }

    @Cacheable(
            key = "'brand-' + #brand + '-model-' + #model + '-memory-' + #memoryAmount"
    )
    public List<Advertisement> getAllByBrandAndModelWithMemoryAmount(String brand, String model, String memoryAmount) {
        String parameters = createParameters(
                List.of(new Advertisement.Parameter("phones_brand", brand),
                        new Advertisement.Parameter("phones_model", model),
                        new Advertisement.Parameter("phablet_phones_memory", memoryAmount)
                )
        );
        return adsRepository.findAllByParameters(parameters);
    }

    @Cacheable(key = "'params-' + #parameters.hashCode()")
    public List<Advertisement> getAllByParameters(List<Advertisement.Parameter> parameters) {
        return adsRepository.findAllByParameters(createParameters(parameters));
    }

    @Cacheable(key = "#id")
    public Advertisement getById(UUID id) {
        return adsRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Advertisement not found"));
    }

    @CacheEvict(key = "#id")
    public void delete(UUID id) {
        adsRepository.deleteById(id);
    }

    public boolean existsByLink(Long adId) {
        return adsRepository.existsByAdId(adId);
    }

    public boolean existsByLink(String link) {
        return adsRepository.existsByLink(link);
    }


    public boolean existsByPublishedAt(LocalDateTime dateTime, Long adId, String category) {
        return adsRepository.existsByPublishedAtAndAdIdAndCategory(dateTime, adId, category);
    }


    public Optional<Advertisement> getByPublishedAtdAndAdIdAndCategory(LocalDateTime dateTime, Long adId, String category) {
        return adsRepository.getByPublishedAtdAndAdIdAndCategory(dateTime, adId, category);
    }

    private String createParameters(List<Advertisement.Parameter> parameters) {
        try {
            return jsonMapper.writeValueAsString(parameters);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
