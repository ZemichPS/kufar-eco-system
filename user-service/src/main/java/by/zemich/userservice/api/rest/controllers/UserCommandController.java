package by.zemich.userservice.api.rest.controllers;

import by.zemich.userservice.api.rest.dto.AssignRoleDto;
import by.zemich.userservice.api.rest.dto.ConfirmationCodeDto;
import by.zemich.userservice.api.rest.dto.UserCreateRequestDto;
import by.zemich.userservice.application.command.ActivateUserByEmailCommand;
import by.zemich.userservice.application.command.AssignUserRoleCommand;
import by.zemich.userservice.application.query.dto.UserResponseDto;
import by.zemich.userservice.api.rest.mapper.UserCommandMapper;
import by.zemich.userservice.api.rest.mapper.UserMapper;
import by.zemich.userservice.application.command.UserCommandService;
import by.zemich.userservice.domain.models.commands.RegisterUserCommand;
import by.zemich.userservice.domain.models.user.entity.User;
import by.zemich.userservice.domain.models.user.vo.Role;
import by.zemich.userservice.domain.models.user.vo.UserId;
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

    @PostMapping
    public ResponseEntity<UserResponseDto> register(
            @Valid @RequestBody UserCreateRequestDto dto
    ) {
        RegisterUserCommand command = UserCommandMapper.map(dto);
        User newUser = userCommandService.handle(command);
        UserResponseDto response = UserMapper.map(newUser);
        String location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.getId())
                .toUriString();
        return ResponseEntity.created(URI.create(location)).body(response);
    }

    @PatchMapping("/{IdUser}")
    public ResponseEntity<Void> activate(
            @PathVariable UUID IdUser,
            @RequestBody @Valid ConfirmationCodeDto codeDto
    ) {
        UserId userId = new UserId(IdUser);
        String code = codeDto.getCode();
        ActivateUserByEmailCommand command = new ActivateUserByEmailCommand(userId, code);
        userCommandService.handle(command);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{IdUser}/roles")
    public ResponseEntity<Void> assignRole(
            @PathVariable UUID IdUser,
            @RequestBody AssignRoleDto assignRoleDto
    ) {
        AssignUserRoleCommand command = new AssignUserRoleCommand(
                new UserId(IdUser),
                Role.valueOf(assignRoleDto.getRole())
        );
        userCommandService.handle(command);
        return ResponseEntity.ok().build();
    }


}
