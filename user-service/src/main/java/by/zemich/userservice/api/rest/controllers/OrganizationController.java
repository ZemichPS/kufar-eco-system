package by.zemich.userservice.api.rest.controllers;


import by.zemich.userservice.application.query.dto.OrganizationResponseDto;
import by.zemich.userservice.api.rest.mapper.OrganizationMapper;
import by.zemich.userservice.application.query.OrganizationQueryService;
import by.zemich.userservice.domain.models.organization.entity.Organization;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/organizations")
@RequiredArgsConstructor
public class OrganizationController {
    private final OrganizationQueryService queryService;

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
