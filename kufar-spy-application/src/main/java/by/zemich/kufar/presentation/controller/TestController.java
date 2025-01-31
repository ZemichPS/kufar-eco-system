package by.zemich.kufar.presentation.controller;

import by.zemich.kufar.application.service.DTO.SmartphonesAdsReport;
import by.zemich.kufar.application.service.SmartphonesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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
