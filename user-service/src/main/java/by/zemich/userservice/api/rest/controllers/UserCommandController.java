package by.zemich.userservice.api.rest.controllers;

import by.zemich.userservice.api.rest.dto.AssignRoleDto;
import by.zemich.userservice.api.rest.dto.ConfirmationCodeDto;
import by.zemich.userservice.api.rest.dto.UserCreateRequestDto;
import by.zemich.userservice.application.command.ConfirmRegistrationCodeCommand;
import by.zemich.userservice.domain.model.commands.AssignUserRoleCommand;
import by.zemich.userservice.application.query.dto.UserResponseDto;
import by.zemich.userservice.api.rest.mapper.UserCommandMapper;
import by.zemich.userservice.api.rest.mapper.UserMapper;
import by.zemich.userservice.application.command.UserCommandService;
import by.zemich.userservice.domain.model.commands.RegisterUserCommand;
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

    @PostMapping("/{user_key}/confirmations")
    public ResponseEntity<Void> confirm(
            @PathVariable(name = "user_key") UUID IdUser,
            @Valid @RequestBody ConfirmationCodeDto dto
    ){
        UserId userId = new UserId(IdUser);
        String code = dto.getCode();
        ConfirmRegistrationCodeCommand command = new ConfirmRegistrationCodeCommand(userId, code);
        userCommandService.handle(command);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{user_key}/roles")
    public ResponseEntity<Void> assignRole(
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


}
