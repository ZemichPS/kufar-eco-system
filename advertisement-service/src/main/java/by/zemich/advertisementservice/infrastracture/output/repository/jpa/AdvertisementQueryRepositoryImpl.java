package by.zemich.advertisementservice.infrastracture.output.repository.jpa;

import by.zemich.advertisementservice.domain.dto.AdvertisementFilter;
import by.zemich.advertisementservice.domain.exception.AdvertisementNotFoundException;
import by.zemich.advertisementservice.domain.repository.AdvertisementQueryRepository;
import by.zemich.advertisementservice.domain.dto.FullAdvertisementDto;
import by.zemich.advertisementservice.domain.valueobject.AdvertisementId;
import by.zemich.advertisementservice.infrastracture.output.repository.jpa.api.AdvertisementRepository;
import by.zemich.advertisementservice.infrastracture.output.repository.jpa.entity.AdvertisementEntity;
import by.zemich.advertisementservice.infrastracture.output.repository.jpa.mapper.AdvertisementMapper;
import by.zemich.advertisementservice.infrastracture.output.repository.jpa.specifications.AdvertisementSpecificationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AdvertisementQueryRepositoryImpl implements AdvertisementQueryRepository {

    private final AdvertisementRepository advertisementRepository;

    @Override
    @Transactional(readOnly = true)
    public Optional<FullAdvertisementDto> getFullResponseById(AdvertisementId advertisementId) {
        UUID advertisemntUuid = advertisementId.uuid();
        return advertisementRepository
                .findById(advertisemntUuid)
                .map(AdvertisementMapper::mapToDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FullAdvertisementDto> getPage(AdvertisementFilter filter, Pageable pageable) {
        AdvertisementSpecificationFilter specificationFilter = new AdvertisementSpecificationFilter();
        BeanUtils.copyProperties(filter, specificationFilter);
        Page<AdvertisementEntity> entityPage = advertisementRepository.findAll(specificationFilter.buildSpecification(), pageable);
        return entityPage.map(AdvertisementMapper::mapToDto);
    }
}
