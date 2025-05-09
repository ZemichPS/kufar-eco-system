package by.zemich.userservice.api.rest.mapper;

import by.zemich.userservice.application.query.dto.OrganizationResponseDto;
import by.zemich.userservice.domain.model.organization.entity.Organization;
import lombok.experimental.UtilityClass;

@UtilityClass
public class OrganizationMapper {
    public static OrganizationResponseDto map(Organization organization) {
        return OrganizationResponseDto.builder()
                .id(organization.getOrganizationId().id())
                .name(organization.getName())
                .organizationType(organization.getOrganizationType().getTitle())
                .phoneNumber(organization.getPhoneNumber().number())
                .address(OrganizationResponseDto.Address.builder()
                        .city(organization.getAddress().getCity())
                        .street(organization.getAddress().getStreet())
                        .houseNumber(organization.getAddress().getHouseNumber())
                        .apartmentNumber(organization.getAddress().getApartmentNumber())
                        .region(organization.getAddress().getRegion())
                        .postalCode(organization.getAddress().getPostalCode())
                        .district(organization.getAddress().getDistrict())
                        .build())
                .build();
    }
}