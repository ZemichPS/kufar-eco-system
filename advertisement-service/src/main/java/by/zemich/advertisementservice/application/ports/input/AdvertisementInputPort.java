package by.zemich.advertisementservice.application.ports.input;

import by.zemich.advertisementservice.application.ports.output.AdvertisementEventOutputPort;
import by.zemich.advertisementservice.application.ports.output.AdvertisementPerststenceOutputPort;
import by.zemich.advertisementservice.application.usecases.AdvertisementCommandUseCases;
import by.zemich.advertisementservice.domain.command.*;
import by.zemich.advertisementservice.domain.entity.Advertisement;
import by.zemich.advertisementservice.domain.entity.AdvertisementAttribute;
import by.zemich.advertisementservice.domain.exception.AdvertisementNotFoundException;
import by.zemich.advertisementservice.domain.valueobject.AdvertisementAttributeId;
import by.zemich.advertisementservice.domain.valueobject.AdvertisementId;

import java.util.UUID;

public class AdvertisementInputPort implements AdvertisementCommandUseCases {

    private final AdvertisementPerststenceOutputPort advertisementPerststenceOutputPort;
    private final AdvertisementEventOutputPort advertisementEventOutputPort;

    public AdvertisementInputPort(AdvertisementPerststenceOutputPort advertisementPerststenceOutputPort,
                                  AdvertisementEventOutputPort advertisementEventOutputPort) {
        this.advertisementPerststenceOutputPort = advertisementPerststenceOutputPort;
        this.advertisementEventOutputPort = advertisementEventOutputPort;
    }

    @Override
    public AdvertisementId handle(CreateAdvertisementCommand command) {
        Advertisement newAdvertisement = new Advertisement(command);
        advertisementPerststenceOutputPort.saveNew(newAdvertisement);
        advertisementEventOutputPort.publishAdvertisementCreated(newAdvertisement);
        return newAdvertisement.getId();
    }

    @Override
    public void handle(UpdateAdvertisementCommand command) {
        Advertisement advertisement = retrieveByIdOrFromRepoOrThrow(command.advertisementId());
        advertisement.changeComment(command.comment());
        advertisement.changeCondition(command.condition());
        advertisement.changePrice(command.price());
        advertisementPerststenceOutputPort.update(advertisement);
    }

    @Override
    public void handle(DeleteAdvertisementCommand command) {
        AdvertisementId id = command.advertisementId();
        if (advertisementPerststenceOutputPort.existsById(id))
            throw new AdvertisementNotFoundException(id.uuid().toString());
        advertisementPerststenceOutputPort.delete(id);
    }

    @Override
    public void handle(AddAttributesCommand command) {
        Advertisement advertisement = retrieveByIdOrFromRepoOrThrow(command.advertisementId());
        command.attributes().entrySet().stream()
                .map(entry ->
                        new AdvertisementAttribute(
                                new AdvertisementAttributeId(UUID.randomUUID()),
                                entry.getKey(),
                                entry.getValue()
                        )
                )
                .forEach(advertisement.getAttributes()::add);
        advertisementPerststenceOutputPort.update(advertisement);
    }

    @Override
    public void handle(UpdateAdvertisementPriceCommand command) {
        Advertisement advertisement = retrieveByIdOrFromRepoOrThrow(command.advertisementId());
        advertisement.changePrice(command.price());
        advertisementPerststenceOutputPort.update(advertisement);
    }

    @Override
    public void handle(ActivateAdvertisementCommand command) {
        Advertisement advertisement = retrieveByIdOrFromRepoOrThrow(command.advertisementId());
        advertisement.activate();
        advertisementPerststenceOutputPort.update(advertisement);
    }

    @Override
    public void handle(DeactivateAdvertisementCommand command) {
        Advertisement advertisement = retrieveByIdOrFromRepoOrThrow(command.advertisementId());
        advertisement.deactivate();
        advertisementPerststenceOutputPort.update(advertisement);
    }

    private Advertisement retrieveByIdOrFromRepoOrThrow(AdvertisementId id) {
        return advertisementPerststenceOutputPort.retrieveById(id).orElseThrow(() -> new AdvertisementNotFoundException(id.uuid().toString()));
    }
}
