package by.zemich.advertisementservice.interfaces.rest.data.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class AdvertisementDto {
    private UUID id;
    private UserDto userDto;
    private String category;
    private String condition;
    private LocalDateTime publishedAt;
    private LocalDateTime activatedAt;
    private BigDecimal priceInByn;
    private String comment;
    private String fileName;
    private boolean active;
    private List<AdvertisementAttributeDto> attributes;
}
