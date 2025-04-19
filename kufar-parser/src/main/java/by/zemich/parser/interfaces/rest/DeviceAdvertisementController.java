package by.zemich.parser.interfaces.rest;

import by.zemich.parser.application.service.AdvertisementService;
import by.zemich.parser.domain.model.Advertisement;
import by.zemich.parser.interfaces.rest.controller.dto.response.DeviceAdvertisementDto;
import by.zemich.parser.interfaces.rest.controller.mapper.DeviceAdvertisementMapper;
import by.zemich.parser.interfaces.rest.controller.mapper.SellerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/advertisements/devices/by_models")
public class DeviceAdvertisementController extends by.zemich.parser.interfaces.controller.BaseController {

    private final AdvertisementService advertisementService;

    @GetMapping
    ResponseEntity<List<DeviceAdvertisementDto>> getByModel(
            @RequestParam String brand,
            @RequestParam String model
    ){
        List<DeviceAdvertisementDto> response = advertisementService.getAllByBrandAndModel(brand, model).stream()
                .map(ad-> {
                    DeviceAdvertisementDto deviceAdvertisementDto = DeviceAdvertisementMapper.mapToDto(ad);
                    deviceAdvertisementDto.setAdOwnerDto(SellerMapper.mapToDto(ad.getSeller()));
                    return deviceAdvertisementDto;
                })
                .toList();
        return ResponseEntity.ok(response);
    }
}
