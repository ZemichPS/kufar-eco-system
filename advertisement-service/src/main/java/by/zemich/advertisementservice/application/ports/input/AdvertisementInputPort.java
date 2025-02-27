package by.zemich.advertisementservice.application.ports.input;

import by.zemich.advertisementservice.application.ports.output.AdvertisementOutputPort;
import by.zemich.advertisementservice.application.ports.output.CategoryPersistenceOutputPort;
import by.zemich.advertisementservice.application.ports.output.AdvertisementEventOutputPort;
import by.zemich.advertisementservice.application.usecases.AdvertisementUseCase;
import by.zemich.advertisementservice.domain.entity.Advertisement;
import by.zemich.advertisementservice.domain.entity.Category;
import by.zemich.advertisementservice.domain.entity.factory.AdvertisementAttributeFactory;
import by.zemich.advertisementservice.domain.entity.factory.AdvertisementFactory;
import by.zemich.advertisementservice.domain.request.Pagination;
import by.zemich.advertisementservice.domain.valueobject.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class AdvertisementInputPort implements AdvertisementUseCase {

    private final CategoryPersistenceOutputPort categoryPersistenceOutputPort;
    private final AdvertisementOutputPort advertisementOutputPort;
    private final AdvertisementEventOutputPort advertisementEventOutputPort;

    public AdvertisementInputPort(CategoryPersistenceOutputPort categoryPersistenceOutputPort,
                                  AdvertisementOutputPort advertisementOutputPort,
                                  AdvertisementEventOutputPort advertisementEventOutputPort) {
        this.categoryPersistenceOutputPort = categoryPersistenceOutputPort;
        this.advertisementOutputPort = advertisementOutputPort;
        this.advertisementEventOutputPort = advertisementEventOutputPort;
    }

    @Override
    public Id create(Id userId, Id categoryId, Condition condition, Price price, Comment comment, Photo photo, Map<UUID, String> attributesMap) {
        Category category = categoryPersistenceOutputPort.getById(categoryId);
        Advertisement createdAdvertisement = AdvertisementFactory.get(
                new Id(UUID.randomUUID()),
                userId,
                category,
                condition,
                LocalDateTime.now(),
                LocalDateTime.now(),
                price,
                comment,
                photo
        );
        AdvertisementAttributeFactory.get(category, attributesMap)
                .forEach(createdAdvertisement::addAttribute);
        advertisementOutputPort.saveNew(createdAdvertisement);
        advertisementEventOutputPort.publishAdvertisementCreated(createdAdvertisement);
        return createdAdvertisement.getId();
    }

    @Override
    public Advertisement update(Id id, Id userId, Id categoryId, Condition condition, Price price, Comment comment, Photo photo, Map<UUID, String> attributesMap) {
        Category category = categoryPersistenceOutputPort.getById(categoryId);
        Advertisement updatedAdvertisement = AdvertisementFactory.get(
                id,
                userId,
                category,
                condition,
                LocalDateTime.now(),
                LocalDateTime.now(),
                price,
                comment,
                photo
        );
        AdvertisementAttributeFactory.get(category, attributesMap)
                .forEach(updatedAdvertisement::addAttribute);
        advertisementOutputPort.update(updatedAdvertisement);
        advertisementEventOutputPort.publishAdvertisementUpdated(updatedAdvertisement);
        return updatedAdvertisement;
    }

    @Override
    public Advertisement updatePriceById(Id advertisementId, Price price) {
        Advertisement advertisement = advertisementOutputPort.retrieveById(advertisementId);
        advertisement.setPrice(price);
        advertisementOutputPort.update(advertisement);
        advertisementEventOutputPort.publishAdvertisementPriceChanged(advertisement);
        return advertisement;
    }

    @Override
    public Advertisement getById(Id advertisementId) {
        return advertisementOutputPort.retrieveById(advertisementId);
    }

    @Override
    public Advertisement activate(Id advertisementId) {
        Advertisement advertisement = advertisementOutputPort.retrieveById(advertisementId);
        advertisement.setActive(true);
        advertisement.setActivatedAt(LocalDateTime.now());
        advertisementOutputPort.saveNew(advertisement);
        return advertisement;
    }

    @Override
    public Advertisement deactivate(Id advertisementId) {
        Advertisement advertisement = advertisementOutputPort.retrieveById(advertisementId);
        advertisement.setActive(false);
        advertisementOutputPort.saveNew(advertisement);
        advertisementEventOutputPort.publishAdvertisementDeactivate(advertisement);
        return advertisement;
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
