package by.zemich.kufar.infrastructure.scheduler;

import by.zemich.kufar.application.service.*;
import by.zemich.kufar.infrastructure.clients.KufarClient;
import by.zemich.kufar.infrastructure.utils.Mapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@EnableScheduling
@RequiredArgsConstructor
@Slf4j
public class GeoScheduledService {
    private final GeoService geoService;
    private final KufarClient kufarClient;

    @Scheduled(initialDelay = 10_000, fixedDelay = 21_600_000)
    public void updateGeoData() {
        kufarClient.getGeoData().stream()
                .map(Mapper::mapToEntity)
                .forEach(geoService::save);
    }
}
