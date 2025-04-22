package by.zemich.advertisementservice.interfaces.messaging.kafka.listener;

import by.zemich.advertisementservice.interfaces.messaging.kafka.mapper.CommandMapper;
import lombok.RequiredArgsConstructor;
import org.apache.avro.generic.GenericRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdvertisementCommandListener {

    private final AdvertisementUseCases advertisementUseCases;

    @KafkaListener(groupId = "advertisementGroup", topics = "createAdvertisement")
    public void listen(GenericRecord record) {
        AdvertisementCreateCommand command = CommandMapper.mapToCommand(record);
        advertisementUseCases.create(
                command.getUserId(),
                command.getCategoryId(),
                command.getCondition(),
                command.getPrice(),
                command.getComment(),
                command.getPhoto(),
                command.getAttributesMap()
        );
    }
}
