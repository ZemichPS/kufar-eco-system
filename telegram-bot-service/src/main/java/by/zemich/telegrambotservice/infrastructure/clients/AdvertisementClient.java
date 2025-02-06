package by.zemich.telegrambotservice.infrastructure.clients;

import by.zemich.telegrambotservice.infrastructure.clients.dto.DeviceAdvertisementsData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("advertisement-service")
public interface AdvertisementClient {

    @RequestMapping(method = RequestMethod.GET, value = "api/v1/advertisements")
    DeviceAdvertisementsData getAdvertisementDeviceData(String brand, String model, String memoryAmount, String ram);

}

