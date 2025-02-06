package by.zemich.parser.presentation.controller;

import by.zemich.parser.application.service.DTO.SmartphonesAdsReport;
import by.zemich.parser.application.service.SmartphonesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/test")
@RequiredArgsConstructor
public class TestController {

    private final SmartphonesService smartphonesService;

    @GetMapping("/report")
    ResponseEntity<SmartphonesAdsReport> getSmartphonesAdsReport(
            @RequestParam String brand,
            @RequestParam String model
    ) {
        SmartphonesAdsReport report = smartphonesService.getReport(brand, model);
        return ResponseEntity.ok(report);
    }

}
