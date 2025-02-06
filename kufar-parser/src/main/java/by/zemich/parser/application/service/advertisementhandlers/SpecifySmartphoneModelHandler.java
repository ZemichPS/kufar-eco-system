package by.zemich.parser.application.service.advertisementhandlers;

import by.zemich.parser.application.service.advertisementhandlers.api.AdvertisementProcessor;
import by.zemich.parser.domain.model.Advertisement;
import by.zemich.parser.domain.model.Model;
import by.zemich.parser.application.service.ManufactureService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Comparator;

@Component
@RequiredArgsConstructor
@Slf4j
public class SpecifySmartphoneModelHandler implements AdvertisementProcessor {

    private final ManufactureService manufactureService;

    @Override
    public void process(Advertisement advertisement) {
        if (advertisement.getBrand().isEmpty()) return;
        if (advertisement.getBrand().get().equalsIgnoreCase("Другие марки")) return;

        String adSubject = advertisement.getSubject();
        adSubject = processString(adSubject);

        String brand = advertisement.getBrand().get();
        try {
            String finalAdSubject = adSubject;
            String model = manufactureService.getAllModelsByManufacturer(brand).stream()
                    .map(Model::getName)
                    .sorted(Comparator.comparingInt(String::length).reversed())
                    .filter(model1 -> finalAdSubject.toLowerCase().contains(model1.toLowerCase()))
                    .findFirst().orElse("");

            if (model.isEmpty()) return;

            advertisement.getParameterByIdentity("phones_model")
                    .ifPresentOrElse(parameter -> parameter.setValue(model),
                            () -> advertisement.addParameter(Advertisement.Parameter.builder()
                                    .label("Модель")
                                    .value(model)
                                    .identity("phones_model")
                                    .build()));
        } catch (Exception e) {
            log.error("Failed to get model name: {}", e.getMessage(), e.getCause());
        }
    }

    @Override
    public boolean canProcess(Advertisement advertisement) {
        return advertisement.getCategory().equalsIgnoreCase("17010") &&
                advertisement.getBrand().isPresent() &&
                advertisement.getModel().isEmpty();
    }

    private String processString(String input) {
        String regexRedmi = "(?i)(Redmi\\s+)(\\d+)(\\s+)([A-Za-z])";
        String regexIphone = "(?i)(айфон\\s+)";
        String regexRebmi = "(?i)(rebmi\\s+)";
        String regexHonor = "(?i)(хонор\\s+)";
        String regexPro = "(?i)(про\\s+)";
        String regexNot = "(?i)(not\\s+)";

        input = input.replaceAll(regexRedmi, "$1$2$4");
        input = input.replaceAll(regexHonor, "Honor");
        input = input.replaceAll(regexIphone, "Iphone");
        input = input.replaceAll(regexPro, "Pro");
        input = input.replaceAll(regexRebmi, "Redmi");
        input = input.replaceAll(regexNot, "Note");
        input = input.replaceAll("\\s+", " ").trim();
        return input;
    }
}
