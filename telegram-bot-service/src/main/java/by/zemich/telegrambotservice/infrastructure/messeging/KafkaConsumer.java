package by.zemich.telegrambotservice.infrastructure.messeging;

import by.zemich.telegrambotservice.application.service.PublishService;
import by.zemich.telegrambotservice.domain.model.KufarAdvertisement;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaConsumer {
    private final PublishService publishService;

    public void consume(KufarAdvertisement kufarAdvertisement) {
        publishService.publish(kufarAdvertisement);
    }
}
