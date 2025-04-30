package by.zemich.device_catalog_service.api.rest;

import by.zemich.device_catalog_service.domen.dto.BrandDto;
import by.zemich.device_catalog_service.service.BrandServiceFacade;
import by.zemich.device_catalog_service.service.DataProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/brands")
@RequiredArgsConstructor
@Slf4j
public class BrandController {

    private final BrandServiceFacade brandServiceFacade;
    private final DataProvider dataProvider;

    @GetMapping
    public ResponseEntity<List<BrandDto>> getAllBrands() {
        List<BrandDto> response = brandServiceFacade.getAllBrands();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/response")
    public ResponseEntity<List<BrandDto>> response() {
        List<BrandDto> data = dataProvider.getData();
        log.info("Producer count: {}", data.size());
        data.forEach(brandServiceFacade::saveBrand);
        return ResponseEntity.ok(data);
    }

}
