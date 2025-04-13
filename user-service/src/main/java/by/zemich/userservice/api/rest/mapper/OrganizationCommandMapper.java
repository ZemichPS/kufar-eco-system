package by.zemich.userservice.api.rest.mapper;

import by.zemich.userservice.api.rest.dto.OrganizationRequestDto;
import by.zemich.userservice.domain.models.commands.CreateOrganizationCommand;
import lombok.experimental.UtilityClass;

@UtilityClass
public class OrganizationCommandMapper {
    public static CreateOrganizationCommand map(OrganizationRequestDto request) {
        return new CreateOrganizationCommand(
                request.getName(),
                request.getOrganizationType(),
                request.getOwnerId(),
                request.getPhoneNumber(),
                request.getPostalCode(),
                request.getRegion(),
                request.getDistrict(),
                request.getCity(),
                request.getStreet(),
                request.getHouseNumber(),
                request.getApartmentNumber()
        );
    }
}
