package by.zemich.userservice.api.rest.controllers;


import by.zemich.userservice.api.rest.dto.OrganizationRequestDto;
import by.zemich.userservice.application.command.OrganizationCommandService;
import by.zemich.userservice.domain.model.commands.CreateOrganizationCommand;
import by.zemich.userservice.domain.model.organization.entity.Organization;
import by.zemich.userservice.domain.model.organization.vo.OrganizationId;
import by.zemich.userservice.domain.model.organization.vo.OrganizationType;
import by.zemich.userservice.domain.model.organization.vo.PhoneNumber;
import by.zemich.userservice.domain.model.user.vo.UserId;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/organizations")
@RequiredArgsConstructor
public class OrganizationCommandController {
    private final OrganizationCommandService commandService;


    @PostMapping
    public ResponseEntity<URI> create(@RequestBody @Valid OrganizationRequestDto dto) {
        CreateOrganizationCommand command = new CreateOrganizationCommand(
                new OrganizationId(UUID.randomUUID()),
                new UserId(dto.getOwnerId()),
                dto.getName(),
                dto.getSpecialization(),
                OrganizationType.valueOf(dto.getOrganizationType()),
                new PhoneNumber(dto.getPhoneNumber()),
                dto.getPostalCode(),
                dto.getRegion(),
                dto.getDistrict(),
                dto.getCity(),
                dto.getStreet(),
                dto.getHouseNumber(),
                dto.getApartmentNumber()
        );

        Organization organization = commandService.handle(command);
        String location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(organization.getOrganizationId().id())
                .toUriString();
        return ResponseEntity.created(URI.create(location)).build();
    }

}
