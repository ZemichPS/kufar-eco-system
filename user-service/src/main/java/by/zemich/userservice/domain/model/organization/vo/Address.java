package by.zemich.userservice.domain.model.organization.vo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Address {
    private String postalCode;
    private String region;
    private String district;        // Район (если применимо)
    private String city;
    private String street;
    private String houseNumber;
    private String apartmentNumber;
}
