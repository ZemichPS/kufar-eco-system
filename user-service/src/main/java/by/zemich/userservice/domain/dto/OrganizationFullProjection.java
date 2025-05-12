package by.zemich.userservice.domain.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public interface OrganizationFullProjection {
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

        String getFirstName();

        String getLastName();

        String getRole();

        LocalDateTime getRegistrationDate();

        String getEmail();

        String getTelegramUserId();

        String getPhoneNumber();

        Boolean getEnabled();
    }
}

