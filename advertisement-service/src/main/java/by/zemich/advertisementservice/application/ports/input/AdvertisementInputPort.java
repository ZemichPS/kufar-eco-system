package by.zemich.advertisementservice.application.ports.input;

import by.zemich.advertisementservice.application.ports.output.AdvertisementOutputPort;
import by.zemich.advertisementservice.application.ports.output.CategoryOutputPort;
import by.zemich.advertisementservice.application.ports.output.AdvertisementEventOutputPort;
import by.zemich.advertisementservice.application.usecases.AdvertisementUseCase;
import by.zemich.advertisementservice.domain.entity.Advertisement;
import by.zemich.advertisementservice.domain.entity.Category;
import by.zemich.advertisementservice.domain.entity.User;
import by.zemich.advertisementservice.domain.entity.factory.AdvertisementAttributeFactory;
import by.zemich.advertisementservice.domain.entity.factory.AdvertisementFactory;
import by.zemich.advertisementservice.domain.exception.EntityNotFoundException;
import by.zemich.advertisementservice.domain.request.Pagination;
import by.zemich.advertisementservice.domain.valueobject.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class AdvertisementInputPort implements AdvertisementUseCase {

    private final CategoryOutputPort categoryOutputPort;
    private final AdvertisementOutputPort advertisementOutputPort;
    private final AdvertisementEventOutputPort advertisementEventOutputPort;

    public AdvertisementInputPort(CategoryOutputPort categoryOutputPort,
                                  AdvertisementOutputPort advertisementOutputPort,
                                  AdvertisementEventOutputPort advertisementEventOutputPort) {
        this.categoryOutputPort = categoryOutputPort;
        this.advertisementOutputPort = advertisementOutputPort;
        this.advertisementEventOutputPort = advertisementEventOutputPort;
    }

    @Override
    public Id create(Id userId, Id categoryId, Condition condition, Price price, Comment comment, Photo photo, Map<UUID, String> attributesMap) {
        Category category = categoryOutputPort.getById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));
        User user = new User(); // TODO получить юзера из feign user service

        Advertisement createdAdvertisement = AdvertisementFactory.create(
                user,
                category,
                condition,
                LocalDateTime.now(),
                LocalDateTime.now(),
                price,
                comment,
                photo
        );

        attributesMap.entrySet().stream()
                .map(entry -> {
                    Id CategoryAttributeId = new Id(entry.getKey());
                    CategoryAttribute attribute = category.getAttributes().stream()
                            .filter(attr -> CategoryAttributeId.equals(attr.id()))
                            .findFirst().orElseThrow(); // TODO продумать
                    String attributeValue = entry.getValue();
                    return AdvertisementAttributeFactory.create(attribute, attributeValue);
                }).forEach(createdAdvertisement::addAttribute);

        advertisementOutputPort.persist(createdAdvertisement);
        advertisementEventOutputPort.publishAdvertisementCreated(createdAdvertisement);
        return createdAdvertisement.getId();
    }

    @Override
    public Advertisement updatePriceById(Id advertisementId, Price price) {
        Advertisement ad = advertisementOutputPort.retrieveById(advertisementId)
                .map(advertisement -> {
                    advertisement.setPrice(price);
                    advertisementOutputPort.persist(advertisement);
                    return advertisement;
                })
                .orElseThrow(() -> new EntityNotFoundException("Advertisement not found"));
        advertisementEventOutputPort.publishAdvertisementPriceChanged(ad);
        return ad;
    }

    @Override
    public Advertisement getById(Id advertisementId) {
        return advertisementOutputPort.retrieveById(advertisementId)
                .orElseThrow(() -> new EntityNotFoundException("Advertisement not found"));
    }

    @Override
    public Advertisement activate(Id advertisementId) {
        return advertisementOutputPort.retrieveById(advertisementId)
                .map(advertisement -> {
                    advertisement.setActive(true);
                    advertisement.setActivatedAt(LocalDateTime.now());
                    advertisementOutputPort.persist(advertisement);
                    return advertisement;
                })
                .orElseThrow(() -> new EntityNotFoundException("Advertisement not found"));
    }

    @Override
    public Advertisement deactivate(Id advertisementId) {
        Advertisement ad = advertisementOutputPort.retrieveById(advertisementId)
                .map(advertisement -> {
                    advertisement.setActive(false);
                    advertisementOutputPort.persist(advertisement);
                    return advertisement;
                })
                .orElseThrow(() -> new EntityNotFoundException("Advertisement not found"));
        advertisementEventOutputPort.publishAdvertisementDeactivate(ad);
        return ad;
    }

    @Override
    public List<Advertisement> getAllActive(Pagination pagination) {
        return advertisementOutputPort.retrieveAllActive(pagination);
    }

    @Override
    public List<Advertisement> getAll(Pagination pagination) {
        return advertisementOutputPort.retrieveAll(pagination);
    }

    @Override
    public List<Advertisement> getAllByUserId(Id userId) {
        return advertisementOutputPort.retrieveAllByUserId(userId);
    }

}
