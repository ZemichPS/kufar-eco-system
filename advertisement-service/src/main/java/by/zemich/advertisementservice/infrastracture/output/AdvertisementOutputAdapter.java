package by.zemich.advertisementservice.infrastracture.output;

import by.zemich.advertisementservice.application.ports.output.AdvertisementOutputPort;
import by.zemich.advertisementservice.domain.entity.Advertisement;
import by.zemich.advertisementservice.domain.request.Pagination;
import by.zemich.advertisementservice.domain.valueobject.Id;
import by.zemich.advertisementservice.infrastracture.output.repository.jpa.api.AdvertisementRepository;
import by.zemich.advertisementservice.infrastracture.output.repository.jpa.entity.AdvertisementAttributeEntity;
import by.zemich.advertisementservice.infrastracture.output.repository.jpa.entity.AdvertisementEntity;
import by.zemich.advertisementservice.infrastracture.output.repository.jpa.entity.CategoryAttributeEntity;
import by.zemich.advertisementservice.infrastracture.output.repository.jpa.entity.CategoryEntity;
import by.zemich.advertisementservice.infrastracture.output.repository.jpa.mapper.AdvertisementAttributeMapper;
import by.zemich.advertisementservice.infrastracture.output.repository.jpa.mapper.AdvertisementMapper;
import by.zemich.advertisementservice.infrastracture.output.repository.jpa.mapper.CategoryAttributeMapper;
import by.zemich.advertisementservice.infrastracture.output.repository.jpa.mapper.CategoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AdvertisementOutputAdapter implements AdvertisementOutputPort {

    private final AdvertisementRepository advertisementRepository;

    @Override
    public void persist(Advertisement advertisement) {
        AdvertisementEntity advertisementEntity = AdvertisementMapper.mapToEntity(advertisement);
        advertisement.getAttributes().stream()
                .map(advertisementAttribute -> {
                    AdvertisementAttributeEntity attributeEntity = AdvertisementAttributeMapper.mapToEntity(advertisementAttribute);
                    CategoryAttributeEntity categoryAttributeEntity = CategoryAttributeMapper.mapToEntity(advertisementAttribute.getCategoryAttribute());
                    CategoryEntity categoryEntity = CategoryMapper.mapToEntity(advertisement.getCategory());
                   // categoryAttributeEntity.setCategory(categoryEntity);
                    attributeEntity.setCategoryAttribute(categoryAttributeEntity);
                    return attributeEntity;
                })
                .forEach(advertisementEntity::addAttribute);
    }

    @Override
    public Optional<Advertisement> retrieveById(Id id) {
        UUID advertisementId = id.uuid();
        return advertisementRepository.findById(advertisementId)
                .map(AdvertisementMapper::mapToDomain)
                .or(Optional::empty);
    }


    @Override
    public List<Advertisement> retrieveAll(Pagination pagination) {
        Sort.Direction sortDirection = pagination.isAsc() ? Sort.Direction.ASC : Sort.Direction.DESC;
        boolean active = pagination.isOnlyActive();
        Pageable pageable = PageRequest.of(
                pagination.getPage(),
                pagination.getSize(),
                Sort.by(sortDirection, pagination.getSortBy())
        );
        return advertisementRepository.findAllWithEagerRelationshipsByActiveIs(pageable, active).stream()
                .map(AdvertisementMapper::mapToDomain)
                .toList();
    }

    @Override
    public List<Advertisement> retrieveAllByUserId(Id userId) {
        UUID userUuid = userId.uuid();
        return advertisementRepository.findAllByUserUuid(userUuid).stream()
                .map(AdvertisementMapper::mapToDomain)
                .toList();
    }
}
