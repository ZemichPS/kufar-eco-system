package by.zemich.advertisementservice.application.ports.input;

import by.zemich.advertisementservice.application.ports.output.AdvertisementOutputPort;
import by.zemich.advertisementservice.application.ports.output.CategoryPersistenceOutputPort;
import by.zemich.advertisementservice.application.ports.output.AdvertisementEventOutputPort;
import by.zemich.advertisementservice.application.usecases.AdvertisementCommandUseCases;
import by.zemich.advertisementservice.domain.command.*;
import by.zemich.advertisementservice.domain.entity.Advertisement;
import by.zemich.advertisementservice.domain.entity.AdvertisementAttribute;
import by.zemich.advertisementservice.domain.entity.Category;
import by.zemich.advertisementservice.domain.exception.AdvertisementNotFoundException;
import by.zemich.advertisementservice.domain.valueobject.*;

import java.util.Optional;
import java.util.UUID;

public class AdvertisementInputPort implements AdvertisementCommandUseCases {

    private final AdvertisementOutputPort advertisementOutputPort;
    private final AdvertisementEventOutputPort advertisementEventOutputPort;

    public AdvertisementInputPort(AdvertisementOutputPort advertisementOutputPort,
                                  AdvertisementEventOutputPort advertisementEventOutputPort) {
        this.advertisementOutputPort = advertisementOutputPort;
        this.advertisementEventOutputPort = advertisementEventOutputPort;
    }

    @Override
    public AdvertisementId handle(CreateAdvertisementCommand command) {
        Advertisement newAdvertisement = new Advertisement(command);
        advertisementOutputPort.saveNew(newAdvertisement);
        advertisementEventOutputPort.publishAdvertisementCreated(newAdvertisement);
        return newAdvertisement.getId();
    }

    @Override
    public void handle(UpdateAdvertisementCommand command) {
        advertisementOutputPort
                .retrieveById(command.advertisementId())
                .map(advertisement -> {
                    advertisement.changeComment(command.comment());
                    advertisement.changeCondition(command.condition());
                    advertisement.changePrice(command.price());
                    advertisement.changePhoto(command.photo());
                    return advertisement;
                })
                .map(advertisementOutputPort::update)
                .orElseThrow(()-> new AdvertisementNotFoundException(command.advertisementId().uuid().toString()));
    }

    @Override
    public void handle(AddAttributesCommand command) {
        Advertisement advertisement = advertisementOutputPort
                .retrieveById(command.advertisementId())
                .orElseThrow(()-> new AdvertisementNotFoundException(command.advertisementId().uuid().toString()));

       command.attributes().entrySet().stream()
               .map(entry ->
                     new AdvertisementAttribute(
                             new AdvertisementAttributeId(UUID.randomUUID()),
                             entry.getKey(),
                             entry.getValue()
                     )
               )
               .forEach(advertisement.getAttributes()::add);
        advertisementOutputPort.update(advertisement);
    }

    @Override
    public void handle(UpdateAdvertisementPriceCommand command) {
        advertisementOutputPort
                .retrieveById(command.advertisementId())
                .map(advertisement -> {
                    advertisement.changePrice(command.price());
                    return advertisement;
                })
                .map(advertisementOutputPort::update)
                .orElseThrow(()-> new AdvertisementNotFoundException(command.advertisementId().uuid().toString()));
    }

    @Override
    public void handle(ActivateAdvertisementCommand command) {
        advertisementOutputPort
                .retrieveById(command.advertisementId())
                .map(advertisement -> {
                    advertisement.activate(true);
                    return advertisement;
                })
                .map(advertisementOutputPort::update)
                .orElseThrow(()-> new AdvertisementNotFoundException(command.advertisementId().uuid().toString()));
    }

    @Override
    public void handle(DeactivateAdvertisementCommand command) {
        advertisementOutputPort
                .retrieveById(command.advertisementId())
                .map(advertisement -> {
                    advertisement.activate(true);
                    return advertisement;
                })
                .map(advertisementOutputPort::update)
                .orElseThrow(()-> new AdvertisementNotFoundException(command.advertisementId().uuid().toString()));
    }
}
