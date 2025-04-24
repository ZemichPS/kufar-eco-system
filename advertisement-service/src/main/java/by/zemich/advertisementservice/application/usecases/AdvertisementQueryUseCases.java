package by.zemich.advertisementservice.application.usecases;

import by.zemich.advertisementservice.domain.dto.AdvertisementFilter;
import by.zemich.advertisementservice.domain.dto.FullAdvertisementDto;
import by.zemich.advertisementservice.domain.query.GetFullAdvertisementQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface AdvertisementQueryUseCases {

    FullAdvertisementDto loadPage(GetFullAdvertisementQuery query);

    Page<FullAdvertisementDto> loadPage(AdvertisementFilter filter, Pageable pageable);

}
