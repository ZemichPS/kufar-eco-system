package by.zemich.parser.application.service.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SmartphonesAdsReport {
    private String model;
    private String brand;
    private Long adsCount;
    private BigDecimal marketPrice;
    private LocalDateTime startDate;
    private List<PhoneData> phoneDataList;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PhoneData{
        private LocalDateTime publishedAt;
        private BigDecimal price;
    }
}
