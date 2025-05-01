package by.zemich.geo_service.infrastructure.scheduled;

import by.zemich.geo_service.service.DataProvider;
import by.zemich.geo_service.service.GeoFacadeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
@EnableScheduling
@RequiredArgsConstructor
@Slf4j
public class ScheduledDataUpdater {

    private final DataProvider dataProvider;
    private final GeoFacadeService geoFacadeService;

    @Scheduled(cron = "0 0 0/6 * * *")
    public void update() {
        dataProvider.getData().forEach(geoFacadeService::saveOrUpdate);
        log.info("Geo data successfully updated");
    }

}
