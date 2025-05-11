package by.zemich.userservice.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrganizationFullDto {
    private UUID id;
    private String name;
    private String organizationType;
    private UUID ownerId;
    private String phoneNumber;
    private Address address;
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Address {
        private String postalCode;
        private String region;
        private String district;        // Район (если применимо)
        private String city;
        private String street;
        private String houseNumber;
        private String apartmentNumber;
    }
}
