package by.zemich.userservice.domain.dto;

import java.util.UUID;

public interface OrganizationProjection {
    UUID getId();

    String getName();

    String getSpecialization();

    String getOrganizationType();

    String getPhoneNumber();

    AddressProjection getAddress();

    OwnerProjection getOwner();

    interface AddressProjection {
        String getPostalCode();

        String getRegion();

        String getDistrict();

        String getCity();

        String getStreet();

        String getHouseNumber();

        String getApartmentNumber();
    }

    interface OwnerProjection {
        UUID getId();
    }
}

