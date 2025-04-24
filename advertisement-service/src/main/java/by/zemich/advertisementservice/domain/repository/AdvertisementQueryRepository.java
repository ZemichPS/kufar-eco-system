package by.zemich.advertisementservice.domain.repository;

import by.zemich.advertisementservice.domain.dto.AdvertisementFilter;
import by.zemich.advertisementservice.domain.dto.FullAdvertisementDto;
import by.zemich.advertisementservice.domain.valueobject.AdvertisementId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdvertisementQueryRepository {
    FullAdvertisementDto getFullResponseById(AdvertisementId advertisementId);

    Page<FullAdvertisementDto> getPage(AdvertisementFilter filter, Pageable pageable);

}
