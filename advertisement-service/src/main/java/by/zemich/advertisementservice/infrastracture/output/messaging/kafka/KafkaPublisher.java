package by.zemich.advertisementservice.infrastracture.output.messaging.kafka;

import by.zemich.advertisementservice.application.ports.output.AdvertisementEventOutputPort;
import by.zemich.advertisementservice.domain.entity.Advertisement;
import lombok.RequiredArgsConstructor;
import org.apache.avro.specific.SpecificRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaPublisher implements AdvertisementEventOutputPort {

    private final KafkaTemplate<String, SpecificRecord> kafkaTemplate;

    @Override
    public void publishAdvertisementCreated(Advertisement advertisement) {

    }

    @Override
    public void publishAdvertisementPriceChanged(Advertisement ad) {

    }

    @Override
    public void publishAdvertisementDeactivate(Advertisement ad) {

    }
}
