package by.zemich.device_catalog_service.service;

import by.zemich.device_catalog_service.domen.dto.BrandDto;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
@ConditionalOnProperty(
        prefix = "data-provider",
        name = "resource",
        havingValue = "device-specification"
)
@RequiredArgsConstructor
public class DeviceSpecificationsDataProviderAdapter implements DataProvider {

    private final RestClient restClient;

    @Override
    public List<BrandDto> getData() {
        return null;
    }
}
