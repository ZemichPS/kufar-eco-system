package by.zemich.userservice.api.rest.controllers;

import by.zemich.userservice.api.rest.CommandMapper;
import by.zemich.userservice.api.rest.dto.AssignRoleDto;
import by.zemich.userservice.api.rest.dto.ConfirmationCodeDto;
import by.zemich.userservice.api.rest.dto.OrganizationRequestDto;
import by.zemich.userservice.api.rest.dto.UserCreateRequestDto;
import by.zemich.userservice.domain.commands.ConfirmRegistrationCodeCommand;
import by.zemich.userservice.application.command.OrganizationCommandService;
import by.zemich.userservice.application.command.UserCommandService;
import by.zemich.userservice.domain.dto.UserResponseDto;
import by.zemich.userservice.domain.commands.AssignUserRoleCommand;
import by.zemich.userservice.domain.commands.CreateOrganizationCommand;
import by.zemich.userservice.domain.commands.RegisterUserCommand;
import by.zemich.userservice.domain.commands.UpdateOrganizationCommand;
import by.zemich.userservice.domain.model.organization.entity.Organization;
import by.zemich.userservice.domain.model.organization.vo.OrganizationId;
import by.zemich.userservice.domain.model.organization.vo.OrganizationType;
import by.zemich.userservice.domain.model.organization.vo.PhoneNumber;
import by.zemich.userservice.domain.model.user.entity.User;
import by.zemich.userservice.domain.model.user.vo.Role;
import by.zemich.userservice.domain.model.user.vo.UserId;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;


@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserCommandController {

    private final UserCommandService userCommandService;
    private final OrganizationCommandService commandService;

    @PostMapping
    public ResponseEntity<UserResponseDto> register(
            @Valid @RequestBody UserCreateRequestDto dto
    ) {
        RegisterUserCommand command = CommandMapper.map(dto);
        User newUser = userCommandService.handle(command);
        String location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newUser.getUserId())
                .toUriString();
        return ResponseEntity.created(URI.create(location)).build();
    }

    @PostMapping("/{user_key}/confirmations")
    public ResponseEntity<Void> confirmRegistration(
            @PathVariable(name = "user_key") UUID IdUser,
            @Valid @RequestBody ConfirmationCodeDto dto
    ) {
        UserId userId = new UserId(IdUser);
        String code = dto.getCode();
        ConfirmRegistrationCodeCommand command = new ConfirmRegistrationCodeCommand(userId, code);
        userCommandService.handle(command);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{user_key}/roles")
    public  ResponseEntity<Void> assignRole(
            @PathVariable(name = "user_key") UUID IdUser,
            @RequestBody AssignRoleDto assignRoleDto
    ) {
        AssignUserRoleCommand command = new AssignUserRoleCommand(
                new UserId(IdUser),
                Role.valueOf(assignRoleDto.getRole())
        );
        userCommandService.handle(command);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/user_key/organizations")
    public ResponseEntity<URI> createOrganization(@RequestBody @Valid OrganizationRequestDto dto) {
        CreateOrganizationCommand command =  CommandMapper.map(dto);
        Organization organization = commandService.handle(command);
        String location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(organization.getOrganizationId().id())
                .toUriString();
        return ResponseEntity.created(URI.create(location)).build();
    }

    @PutMapping("/{user_key}/organizations/{organization_key}")
    public ResponseEntity<URI> updateOrganization(
            @PathVariable(name = "user_key") UUID idUser,
            @PathVariable(name = "organization_key") UUID organizationId,
            @RequestBody @Valid OrganizationRequestDto dto
    ) {
        UpdateOrganizationCommand command = CommandMapper.map(dto, idUser, organizationId);
        Organization organization = commandService.handle(command);
        String location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(organization.getOrganizationId().id())
                .toUriString();
        return ResponseEntity.created(URI.create(location)).build();
    }
}
