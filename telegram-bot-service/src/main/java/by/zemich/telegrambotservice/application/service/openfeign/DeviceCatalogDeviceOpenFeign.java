package by.zemich.telegrambotservice.application.service.openfeign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient("device-catalog-service")
public interface DeviceCatalogDeviceOpenFeign {

    @GetMapping("/api/v1/brands")
    List<String> getVendors();

    @GetMapping("/api/v1/brands/{brandName}")
    List<String> getModelsByVendorName(@PathVariable String brandName);
}
