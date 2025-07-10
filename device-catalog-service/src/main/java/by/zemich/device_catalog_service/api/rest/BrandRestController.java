package by.zemich.device_catalog_service.api.rest;

import by.zemich.device_catalog_service.domen.dtos.BrandDto;
import by.zemich.device_catalog_service.domen.dtos.BrandModifyDto;
import by.zemich.device_catalog_service.domen.dtos.ModelCreateDto;
import by.zemich.device_catalog_service.domen.dtos.ModelDto;
import by.zemich.device_catalog_service.service.BrandServiceFacade;
import by.zemich.device_catalog_service.service.DataProvider;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/brands")
@RequiredArgsConstructor
@Slf4j
public class BrandRestController {

    private final BrandServiceFacade brandServiceFacade;
    private final DataProvider dataProvider;

    @GetMapping
    public ResponseEntity<List<BrandDto>> getAllBrands() {
        List<BrandDto> response = brandServiceFacade.getAllBrands();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{brandName}/models")
    public ResponseEntity<List<ModelDto>> getAllModelsByBrandName(String brandName) {
        List<ModelDto> response = brandServiceFacade.getAllModelsByBrandName(brandName);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/id/{uuid}")
    public ResponseEntity<BrandDto> getById(@PathVariable UUID uuid) {
        BrandDto response = brandServiceFacade.getByUuid(uuid);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<BrandDto> getByName(@PathVariable String name) {
        BrandDto response = brandServiceFacade.getByName(name);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<BrandDto> create(@RequestBody @Valid BrandModifyDto dto) {

        BrandDto savedBrand = brandServiceFacade.create(dto);
        return ResponseEntity.ok(savedBrand);
    }

    @PostMapping("{uuid}")
    public ResponseEntity<BrandDto> addModel(@PathVariable(name = "uuid") UUID brandUuid, @RequestBody @Valid ModelCreateDto createDto) {
        BrandDto savedBrand = brandServiceFacade.addModel(brandUuid, createDto);
        return ResponseEntity.ok(savedBrand);
    }

    @PatchMapping("{uuid}")
    public ResponseEntity<BrandDto> update(@PathVariable(name = "uuid") UUID brandUuid, BrandModifyDto updateDto) {
        brandServiceFacade.update(brandUuid, updateDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{uuid}")
    public ResponseEntity<Void> deleteById(@PathVariable(name = "uuid") UUID brandUuid) {
        brandServiceFacade.deleteByUuid(brandUuid);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/response")
    public ResponseEntity<List<BrandDto>> response() {
        List<BrandDto> data = dataProvider.getData();
        log.info("Producer count: {}", data.size());
        data.forEach(brandServiceFacade::saveOrUpdateBrand);
        return ResponseEntity.ok(data);
    }

}
