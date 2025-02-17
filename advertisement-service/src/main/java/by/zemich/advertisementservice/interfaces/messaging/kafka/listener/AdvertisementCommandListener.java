package by.zemich.advertisementservice.interfaces.messaging.kafka.listener;

import by.zemich.advertisementservice.application.usecases.AdvertisementUseCase;
import by.zemich.advertisementservice.domain.command.AdvertisementCreateCommand;
import by.zemich.advertisementservice.interfaces.messaging.kafka.mapper.CommandMapper;
import lombok.RequiredArgsConstructor;
import org.apache.avro.generic.GenericRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdvertisementCommandListener {

    private final AdvertisementUseCase advertisementUseCase

    @KafkaListener(groupId = "advertisementGroup", topics = "createAdvertisement")
    public void listen(GenericRecord record) {
        AdvertisementCreateCommand command = CommandMapper.mapToCommand(record);
        advertisementUseCase.create(
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
