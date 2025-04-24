package by.zemich.advertisementservice.application.ports.input;

import by.zemich.advertisementservice.application.usecases.AdvertisementQueryUseCases;
import by.zemich.advertisementservice.domain.query.GetFullAdvertisementQuery;
import by.zemich.advertisementservice.domain.repository.AdvertisementQueryRepository;
import by.zemich.advertisementservice.domain.response.FullAdvertisementDto;

public class AdvertisementQueryInputPort implements AdvertisementQueryUseCases {

    private final AdvertisementQueryRepository advertisementQueryRepository;

    public AdvertisementQueryInputPort(AdvertisementQueryRepository advertisementQueryRepository) {
        this.advertisementQueryRepository = advertisementQueryRepository;
    }

    @Override
    public FullAdvertisementDto load(GetFullAdvertisementQuery query) {
        return advertisementQueryRepository.getFullResponseById(query.advertisementId());
    }
}
