package by.zemich.userservice.application.query.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class OrganizationRequestDto {
    private String name;
    private String organizationType;
    private UUID ownerId;
    private String phoneNumber;
    private String postalCode;
    private String region;
    private String district;        // Район (если применимо)
    private String city;
    private String street;
    private String houseNumber;
    private String apartmentNumber;

}
