package by.zemich.advertisementservice.interfaces.rest.data.request;

import lombok.Data;

import java.util.Map;
import java.util.UUID;

@Data
public class AttributesDto {
    Map<UUID, String> attributes;
}
