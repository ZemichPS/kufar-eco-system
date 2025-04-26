package by.zemich.advertisementservice.infrastracture.output.messaging.kafka;

import by.zemich.advertisementservice.application.ports.output.AdvertisementEventOutputPort;
import by.zemich.advertisementservice.domain.entity.Advertisement;
import by.zemich.advertisementservice.domain.valueobject.UserId;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.avro.Schema;
import org.apache.avro.specific.SpecificRecord;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class KafkaPublisher implements AdvertisementEventOutputPort {

//    private final KafkaTemplate<String, SpecificRecord> kafkaTemplate;

    @Override
    @SneakyThrows
    public void publishAdvertisementCreated(Advertisement advertisement) {
        SpecificRecord specificRecord = new SpecificRecord() {
            @Override
            public void put(int i, Object v) {

            }

            @Override
            public Object get(int i) {
                return null;
            }

            @Override
            public Schema getSchema() {
                return null;
            }
        };
     //   kafkaTemplate.send("topic", "", specificRecord).get();
        SpecificRecord.class.getCanonicalName();
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
    public void publishPositionFount(List<UserId> userIds, Advertisement advertisement) {

    }
}
