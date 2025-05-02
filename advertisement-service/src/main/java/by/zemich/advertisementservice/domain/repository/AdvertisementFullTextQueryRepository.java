package by.zemich.advertisementservice.domain.repository;

import by.zemich.advertisementservice.domain.dto.FullAdvertisementDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdvertisementFullTextQueryRepository {
    Page<FullAdvertisementDto> fullTextSearch(String query, Pageable pageable);
}
