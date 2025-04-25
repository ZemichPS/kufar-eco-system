package by.zemich.advertisementservice.interfaces.rest.data.response;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class AdvertisementAttributeDto {
    private UUID attributeUuid;
    private String value;
}
