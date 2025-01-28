package by.zemich.kufar.application.service;

import by.zemich.kufar.application.service.advertisementhandlers.api.AdvertisementProcessor;
import by.zemich.kufar.domain.model.Advertisement;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Log4j2
public class AdvertisementPostProcessService {

    private final List<AdvertisementProcessor> advertisementHandlers;
    private final AdvertisementService advertisementService;


    public Advertisement postProcess(Advertisement ad) {
        advertisementHandlers.stream()
                .filter(advertisementHandler -> advertisementHandler.canProcess(ad))
                .forEach(advertisementHandler -> advertisementHandler.process(ad));
        return advertisementService.save(ad);
    }
}
