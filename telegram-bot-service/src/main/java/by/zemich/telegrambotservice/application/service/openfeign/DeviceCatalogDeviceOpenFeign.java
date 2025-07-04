package by.zemich.telegrambotservice.application.service.openfeign;

import org.springframework.cloud.openfeign.FeignClient;

import java.util.List;

@FeignClient("device-catalog")
public interface DeviceCatalogDeviceOpenFeign {

    List<String> getVendors();

    List<String> getModelByVendor(String model);
}
