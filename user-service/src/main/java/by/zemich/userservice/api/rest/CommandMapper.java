package by.zemich.userservice.api.rest;

import by.zemich.userservice.api.rest.dto.OrganizationRequestDto;
import by.zemich.userservice.api.rest.dto.UserCreateRequestDto;
import by.zemich.userservice.domain.command.CreateOrganizationCommand;
import by.zemich.userservice.domain.command.RegisterUserCommand;
import by.zemich.userservice.domain.command.UpdateOrganizationCommand;
import by.zemich.userservice.domain.model.organization.vo.OrganizationId;
import by.zemich.userservice.domain.model.organization.vo.OrganizationType;
import by.zemich.userservice.domain.model.user.vo.*;
import lombok.experimental.UtilityClass;

import java.util.UUID;

@UtilityClass
public class CommandMapper {

    public static RegisterUserCommand map(UserCreateRequestDto requestDto) {
        return new RegisterUserCommand(
                new FullName(requestDto.getFirstName(), requestDto.getLastName()),
                Role.valueOf(requestDto.getRole()),
                new Email(requestDto.getEmail()),
                new PhoneNumber(requestDto.getPhoneNumber()),
                requestDto.getPassword()
        );
    }

    public static CreateOrganizationCommand map(OrganizationRequestDto dto){
        return new CreateOrganizationCommand(
                new OrganizationId(UUID.randomUUID()),
                new UserId(dto.getOwnerId()),
                dto.getName(),
                dto.getSpecialization(),
                OrganizationType.valueOf(dto.getOrganizationType()),
                new by.zemich.userservice.domain.model.organization.vo.PhoneNumber(dto.getPhoneNumber()),
                dto.getPostalCode(),
                dto.getRegion(),
                dto.getDistrict(),
                dto.getCity(),
                dto.getStreet(),
                dto.getHouseNumber(),
                dto.getApartmentNumber()
        );
    }

    public static UpdateOrganizationCommand map(OrganizationRequestDto dto, UUID idUser, UUID organizationId){
        return new UpdateOrganizationCommand(
                new UserId(idUser),
                new OrganizationId(organizationId),
                dto.getName(),
                dto.getSpecialization(),
                OrganizationType.valueOf(dto.getOrganizationType()),
                new by.zemich.userservice.domain.model.organization.vo.PhoneNumber(dto.getPhoneNumber()),
                dto.getPostalCode(),
                dto.getRegion(),
                dto.getDistrict(),
                dto.getCity(),
                dto.getStreet(),
                dto.getHouseNumber(),
                dto.getApartmentNumber()
        );
    }
}
