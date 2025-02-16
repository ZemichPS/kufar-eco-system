package by.zemich.advertisementservice.infrastracture.input.rest.data.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AdvertisementAttributeDto {
    private String name;
    private String value;
}
