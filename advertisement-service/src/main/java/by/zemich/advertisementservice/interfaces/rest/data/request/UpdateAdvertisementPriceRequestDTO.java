package by.zemich.advertisementservice.interfaces.rest.data.request;


import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class UpdateAdvertisementPriceRequestDTO {
    private UUID advertisementId;
    private BigDecimal priceInByn;
}
