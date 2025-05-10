package by.zemich.userservice.infrastructure.persistence.jpa.mapper;

import by.zemich.userservice.domain.model.organization.entity.Organization;
import by.zemich.userservice.domain.model.organization.vo.Address;
import by.zemich.userservice.domain.model.organization.vo.OrganizationId;
import by.zemich.userservice.domain.model.organization.vo.OrganizationType;
import by.zemich.userservice.domain.model.organization.vo.PhoneNumber;
import by.zemich.userservice.domain.model.user.vo.UserId;
import by.zemich.userservice.infrastructure.persistence.jpa.entities.OrganizationEntity;
import lombok.experimental.UtilityClass;

@UtilityClass
public class OrganizationMapper {

    public static Organization map(OrganizationEntity entity) {
        return new Organization(
                new OrganizationId(entity.getId()),
                new UserId(entity.getOwner().getId()),
                entity.getName(),
                OrganizationType.valueOf(entity.getOrganizationType()),
                new PhoneNumber(entity.getPhoneNumber()),
                entity.getSpecialization(),
                Address.builder()
                        .postalCode(entity.getAddress().getPostalCode())
                        .region(entity.getAddress().getRegion())
                        .district(entity.getAddress().getDistrict())
                        .city(entity.getAddress().getCity())
                        .street(entity.getAddress().getStreet())
                        .houseNumber(entity.getAddress().getHouseNumber())
                        .apartmentNumber(entity.getAddress().getApartmentNumber())
                        .build()
        );
    }

    public static OrganizationEntity map(Organization organization) {
        return OrganizationEntity.builder()
                .id(organization.getOrganizationId().id())
                .name(organization.getName())
                .specialization(organization.getSpecialization())
                .organizationType(organization.getOrganizationType().toString())
                .phoneNumber(organization.getPhoneNumber().number())
                .address(by.zemich.userservice.infrastructure.persistence.jpa.entities.Address.builder()
                        .city(organization.getAddress().getCity())
                        .apartmentNumber(organization.getAddress().getApartmentNumber())
                        .district(organization.getAddress().getDistrict())
                        .region(organization.getAddress().getRegion())
                        .street(organization.getAddress().getStreet())
                        .postalCode(organization.getAddress().getPostalCode())
                        .houseNumber(organization.getAddress().getHouseNumber())
                        .build()
                )
                .build();
    }
}
