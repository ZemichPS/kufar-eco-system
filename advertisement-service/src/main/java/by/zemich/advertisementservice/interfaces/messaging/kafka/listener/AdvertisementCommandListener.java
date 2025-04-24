package by.zemich.advertisementservice.interfaces.messaging.kafka.listener;

import by.zemich.advertisementservice.application.usecases.AdvertisementCommandUseCases;
import by.zemich.advertisementservice.domain.command.CreateAdvertisementCommand;
import by.zemich.advertisementservice.interfaces.messaging.kafka.mapper.CommandMapper;
import lombok.RequiredArgsConstructor;
import org.apache.avro.generic.GenericRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdvertisementCommandListener {

    private final AdvertisementCommandUseCases commandUseCases;

    @KafkaListener(groupId = "advertisementGroup", topics = "createAdvertisement")
    public void listen(GenericRecord record) {
        CreateAdvertisementCommand command = CommandMapper.mapToCommand(record);
        commandUseCases.handle(command);
    }
}
