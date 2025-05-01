package by.zemich.geo_service.api.rest;

import by.zemich.geo_service.domain.dtos.GeoDataDto;
import by.zemich.geo_service.service.GeoFacadeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/geo")
@RequiredArgsConstructor
public class GeoDataRestController {

    private final GeoFacadeService geoFacadeService;

    @GetMapping
    public ResponseEntity<List<GeoDataDto>> getGeoData() {
        List<GeoDataDto> response = geoFacadeService.getAll();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GeoDataDto> getGeoDataById(@PathVariable("id") Integer id) {
        GeoDataDto response = geoFacadeService.getGeoDataById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/areas")
    public ResponseEntity<List<GeoDataDto>> getAreasGeoData() {
        List<GeoDataDto> response = geoFacadeService.getAreas();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/cities")
    public ResponseEntity<List<GeoDataDto>> getCitiesGeoData() {
        List<GeoDataDto> response = geoFacadeService.getCities();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/settlements")
    public ResponseEntity<List<GeoDataDto>> getUrbanSettlementsGeoData() {
        List<GeoDataDto> response = geoFacadeService.getUrbanSettlements();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/cities/{region}")
    public ResponseEntity<List<GeoDataDto>> getCitiesByRegions(@PathVariable Integer region) {
        List<GeoDataDto> response = geoFacadeService.getCitiesByRegion(region);
        return ResponseEntity.ok(response);
    }

}
