package by.zemich.userservice.api.rest.controllers;


import by.zemich.userservice.application.query.OrganizationQueryService;
import by.zemich.userservice.domain.dto.OrganizationFullProjection;
import by.zemich.userservice.domain.dto.OrganizationProjection;
import by.zemich.userservice.domain.model.organization.vo.OrganizationId;
import by.zemich.userservice.domain.model.user.vo.UserId;
import by.zemich.userservice.infrastructure.security.annotations.CurrentUserId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/organizations")
@RequiredArgsConstructor
public class OrganizationQueryController {
    private final OrganizationQueryService queryService;

    @GetMapping("/organizations")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<OrganizationFullProjection>> getAllOrganizations() {
        List<OrganizationFullProjection> response = queryService.getAll();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/organizations/my")
    public ResponseEntity<OrganizationProjection> getMyOrganization(@CurrentUserId UserId userId) {
        OrganizationProjection response = queryService.getByOwnerId(userId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/organizations/{organization_key}")
    public ResponseEntity<OrganizationProjection> getOrganizationById(@PathVariable(name = "organization_key") UUID idOrganization) {
        OrganizationId id = new OrganizationId(idOrganization);
        OrganizationProjection response = queryService.getById(id);
        return ResponseEntity.ok(response);
    }

}
