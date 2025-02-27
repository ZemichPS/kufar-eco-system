package by.zemich.telegrambotservice.interfaces.messeging;

import by.zemich.telegrambotservice.application.service.PublishService;
import by.zemich.telegrambotservice.domain.model.KufarAdvertisement;
import by.zemich.telegrambotservice.interfaces.messeging.mapper.AdvertisementMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.generic.GenericRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Slf4j
@Component
public class AdvertisementKafkaListener {
    private final PublishService publishService;

    @KafkaListener(
            topics = "advertisement.created",
            groupId = "telegram-service-group",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void listen(GenericRecord record) {
        KufarAdvertisement advertisement = AdvertisementMapper.map(record);
        publishService.publish(advertisement);
    }
}
