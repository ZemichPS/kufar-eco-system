package by.zemich.advertisementservice.infrastracture.output.messaging.kafka;

import by.zemich.advertisementservice.application.ports.output.AdvertisementEventOutputPort;
import by.zemich.advertisementservice.domain.entity.Advertisement;
import by.zemich.advertisementservice.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.apache.avro.specific.SpecificRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class KafkaPublisher implements AdvertisementEventOutputPort {

    private final KafkaTemplate<String, SpecificRecord> kafkaTemplate;

    @Override
    public void publishAdvertisementCreated(Advertisement advertisement) {


    }

    @Override
    public void publishAdvertisementUpdated(Advertisement advertisement) {

    }

    @Override
    public void publishAdvertisementPriceChanged(Advertisement ad) {

    }

    @Override
    public void publishAdvertisementDeactivate(Advertisement ad) {

    }

    @Override
    public void publishPositionFount(List<User> users, Advertisement advertisement) {

    }
}
