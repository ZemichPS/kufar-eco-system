package by.zemich.userservice.infrastructure.persistence.jpa.mapper;

import by.zemich.userservice.domain.models.organization.entity.Organization;
import by.zemich.userservice.domain.models.organization.vo.Address;
import by.zemich.userservice.domain.models.organization.vo.OrganizationId;
import by.zemich.userservice.domain.models.organization.vo.OrganizationType;
import by.zemich.userservice.domain.models.organization.vo.PhoneNumber;
import by.zemich.userservice.infrastructure.persistence.jpa.entities.OrganizationEntity;
import by.zemich.userservice.infrastructure.persistence.jpa.entities.UserEntity;
import lombok.experimental.UtilityClass;

@UtilityClass
public class OrganizationMapper {
    public static Organization map(OrganizationEntity entity) {
        return new Organization(
                new OrganizationId(entity.getId()),
                entity.getName(),
                OrganizationType.valueOf(entity.getOrganizationType()),
                Address.builder()
                        .city(entity.getAddress().getCity())
                        .apartmentNumber(entity.getAddress().getApartmentNumber())
                        .district(entity.getAddress().getDistrict())
                        .region(entity.getAddress().getRegion())
                        .street(entity.getAddress().getStreet())
                        .postalCode(entity.getAddress().getPostalCode())
                        .houseNumber(entity.getAddress().getHouseNumber())
                        .build(),
                new PhoneNumber(entity.getPhoneNumber()),
                entity.getStaff().stream().toList()
        );
    }

    public static OrganizationEntity map(Organization organization) {
        return OrganizationEntity.builder()
                .id(organization.getOrganizationId().id())
                .address(by.zemich.userservice.infrastructure.persistence.jpa.entities.Address.builder()
                        .city(organization.getAddress().getCity())
                        .apartmentNumber(organization.getAddress().getApartmentNumber())
                        .district(organization.getAddress().getDistrict())
                        .region(organization.getAddress().getRegion())
                        .street(organization.getAddress().getStreet())
                        .postalCode(organization.getAddress().getPostalCode())
                        .houseNumber(organization.getAddress().getHouseNumber())
                        .build())
                .phoneNumber(organization.getPhoneNumber().number())
                .organizationType(organization.getOrganizationType().toString())
                .name(organization.getName())
                .staff(organization.getStaff().stream().toList())
                .build();
    }
}
