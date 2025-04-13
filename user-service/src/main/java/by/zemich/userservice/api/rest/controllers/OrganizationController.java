package by.zemich.userservice.api.rest.controllers;


import by.zemich.userservice.api.rest.dto.OrganizationRequestDto;
import by.zemich.userservice.api.rest.dto.OrganizationResponseDto;
import by.zemich.userservice.api.rest.mapper.OrganizationCommandMapper;
import by.zemich.userservice.api.rest.mapper.OrganizationMapper;
import by.zemich.userservice.application.OrganizationCommandService;
import by.zemich.userservice.application.OrganizationQueryService;
import by.zemich.userservice.domain.models.commands.CreateOrganizationCommand;
import by.zemich.userservice.domain.models.organization.entity.Organization;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/organizations")
@RequiredArgsConstructor
public class OrganizationController {
    private final OrganizationCommandService commandService;
    private final OrganizationQueryService queryService;

    @PostMapping
    public ResponseEntity<OrganizationResponseDto> crete(OrganizationRequestDto request) {
        CreateOrganizationCommand command = OrganizationCommandMapper.map(request);
        Organization organization = commandService.handle(command);
        OrganizationResponseDto response = OrganizationMapper.map(organization);
        String location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.getId())
                .toUriString();
        return ResponseEntity.created(URI.create(location)).body(response);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<OrganizationResponseDto>> getAll() {
        List<OrganizationResponseDto> response = queryService.getAll().stream()
                .map(OrganizationMapper::map)
                .toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{ownerId}")
    public ResponseEntity<OrganizationResponseDto> getByOwnerId(UUID ownerId) {
      //  Organization organization = OrganizationQueryService.getByOwnerId(ownerId);
        return ResponseEntity.ok(null);
    }


}
