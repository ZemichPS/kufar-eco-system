package by.zemich.userservice.api.rest.mapper;

import by.zemich.userservice.application.query.dto.OrganizationRequestDto;
import by.zemich.userservice.domain.models.commands.CreateOrganizationCommand;
import by.zemich.userservice.domain.models.organization.vo.OrganizationId;
import by.zemich.userservice.domain.models.user.vo.UserId;
import lombok.experimental.UtilityClass;

import java.util.UUID;

@UtilityClass
public class OrganizationCommandMapper {
    public static CreateOrganizationCommand map(OrganizationRequestDto request) {
        return new CreateOrganizationCommand(
                new OrganizationId(UUID.randomUUID()),
                new UserId(request.getOwnerId()),
                request.getName(),
                request.getOrganizationType(),
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
