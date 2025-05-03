package by.zemich.advertisementservice.application.ports.input;

import by.zemich.advertisementservice.application.usecases.AdvertisementQueryUseCases;
import by.zemich.advertisementservice.domain.exception.AdvertisementNotFoundException;
import by.zemich.advertisementservice.domain.query.GetFilteredPageQuery;
import by.zemich.advertisementservice.domain.query.GetFilteredPageWithMyAdsQuery;
import by.zemich.advertisementservice.domain.query.GetFullAdvertisementQuery;
import by.zemich.advertisementservice.domain.repository.AdvertisementFullTextQueryRepository;
import by.zemich.advertisementservice.domain.repository.AdvertisementQueryRepository;
import by.zemich.advertisementservice.domain.dto.FullAdvertisementDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class AdvertisementQueryInputPort implements AdvertisementQueryUseCases {

    private final AdvertisementQueryRepository advertisementQueryRepository;
    private final AdvertisementFullTextQueryRepository  advertisementFullTextQueryRepository;

    public AdvertisementQueryInputPort(AdvertisementQueryRepository advertisementQueryRepository,
                                       AdvertisementFullTextQueryRepository advertisementFullTextQueryRepository) {
        this.advertisementQueryRepository = advertisementQueryRepository;
        this.advertisementFullTextQueryRepository = advertisementFullTextQueryRepository;
    }

    @Override
    public FullAdvertisementDto loadPage(GetFullAdvertisementQuery query) {
        return advertisementQueryRepository.getFullResponseById(query.advertisementId()).orElseThrow(
                () -> new AdvertisementNotFoundException(query.advertisementId().uuid().toString())
        );
    }

    @Override
    public Page<FullAdvertisementDto> loadPage(GetFilteredPageQuery query) {
        return advertisementQueryRepository.getPage(query.filter(), query.pageable());
    }

    @Override
    public Page<FullAdvertisementDto> loadMyAdsPage(GetFilteredPageWithMyAdsQuery query) {
        return advertisementQueryRepository.getPage(query.filter(), query.pageable());
    }

    @Override
    public Page<FullAdvertisementDto> fullTextSearch(String query, Pageable pageable) {
        return advertisementFullTextQueryRepository.fullTextSearch(query, pageable);
    }
}
