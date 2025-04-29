package by.zemich.device_catalog_service.api.rest;

import by.zemich.device_catalog_service.domen.dto.BrandDto;
import by.zemich.device_catalog_service.service.BrandServiceFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/brands")
@RequiredArgsConstructor
public class BrandController {

    private final BrandServiceFacade brandServiceFacade;

    @GetMapping
    public ResponseEntity<List<BrandDto>> getAllBrands() {
        List<BrandDto> response = brandServiceFacade.getAllBrands();
        return ResponseEntity.ok(response);
    }

}
