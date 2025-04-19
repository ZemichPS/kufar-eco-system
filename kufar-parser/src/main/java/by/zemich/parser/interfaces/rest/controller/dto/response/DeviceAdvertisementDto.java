package by.zemich.parser.interfaces.rest.controller.dto.response;

import by.zemich.parser.domain.model.Advertisement;
import by.zemich.parser.domain.model.Seller;
import by.zemich.parser.interfaces.rest.controller.dto.AdOwnerDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class DeviceAdvertisementDto {
    private UUID id;
    private Long adId;
    private String link;
    private String category;
    private boolean companyAd;
    private String currency;
    private LocalDateTime publishedAt;
    private String subject;
    private String type;
    private BigDecimal priceInByn;
    private BigDecimal priceInUsd;
    private String details;
    private boolean fullyFunctional;
    private AdOwnerDto adOwnerDto;
    private List<DeviceAdvertisementDto.Parameter> parameters = new ArrayList<>();
    private String brand;
    private String model;
    private String fullAddress;
    private String condition;
    private String photoLink;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Parameter {
        public Parameter(String identity, String value) {
            this.identity = identity;
            this.value = value;
        }

        private String identity;
        private String value;
        private String label;
    }

}
