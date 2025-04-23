package by.zemich.advertisementservice.application.ports.input;

import by.zemich.advertisementservice.application.usecases.AdvertisementQueryUseCases;
import by.zemich.advertisementservice.domain.entity.Advertisement;
import by.zemich.advertisementservice.domain.exception.AdvertisementNotFoundException;
import by.zemich.advertisementservice.domain.query.GetFullAdvertisementQuery;
import by.zemich.advertisementservice.domain.response.FullAdvertisementResponse;
import by.zemich.advertisementservice.infrastracture.output.repository.jpa.api.AdvertisementRepository;

import java.util.UUID;

public class AdvertisementQueryInputPort implements AdvertisementQueryUseCases {

    private final AdvertisementRepository advertisementRepository;

    public AdvertisementQueryInputPort(AdvertisementRepository advertisementRepository) {
        this.advertisementRepository = advertisementRepository;
    }

    @Override
    public FullAdvertisementResponse load(GetFullAdvertisementQuery query) {
        UUID advertisemntUuid = query.advertisementId().uuid();
        FullAdvertisementResponse dto = advertisementRepository
                .findById(advertisemntUuid)
                .map(entity->{
                    return FullAdvertisementResponse.builder()
                            .uuid(entity.getUuid())
                            .userUuid(entity.getUserUuid())
                            .category(entity.getCategory().getName())
                            .condition(entity.getCondition().getConditionDescription())
                            .publishedAt(entity.getPublishedAt())
                            .comment(entity.getComment())
                            .side(entity.getSide().getSideDescription())
                            .build();
                }).orElseThrow(()-> new AdvertisementNotFoundException(advertisemntUuid.toString()));

            return dto;
    }
}
