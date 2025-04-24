package by.zemich.advertisementservice.infrastracture.output.repository.jpa;

import by.zemich.advertisementservice.domain.exception.AdvertisementNotFoundException;
import by.zemich.advertisementservice.domain.repository.AdvertisementQueryRepository;
import by.zemich.advertisementservice.domain.response.FullAdvertisementDto;
import by.zemich.advertisementservice.domain.valueobject.AdvertisementId;
import by.zemich.advertisementservice.infrastracture.output.repository.jpa.api.AdvertisementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AdvertisementQueryRepositoryImpl implements AdvertisementQueryRepository {

    private final AdvertisementRepository advertisementRepository;

    @Override
    public FullAdvertisementDto getFullResponseById(AdvertisementId advertisementId) {
        UUID advertisemntUuid = advertisementId.uuid();
        FullAdvertisementDto dto = advertisementRepository
                .findById(advertisemntUuid)
                .map(entity -> {
                    return FullAdvertisementDto.builder()
                            .uuid(entity.getUuid())
                            .userUuid(entity.getUserUuid())
                            .category(entity.getCategory().getName())
                            .condition(entity.getCondition().getConditionDescription())
                            .publishedAt(entity.getPublishedAt())
                            .comment(entity.getComment())
                            .side(entity.getSide().getSideDescription())
                            .build();
                }).orElseThrow(() -> new AdvertisementNotFoundException(advertisemntUuid.toString()));

        return dto;
    }
}
