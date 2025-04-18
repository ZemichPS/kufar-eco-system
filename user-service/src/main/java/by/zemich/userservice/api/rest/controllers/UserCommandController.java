package by.zemich.userservice.api.rest.controllers;

import by.zemich.userservice.api.rest.dto.UserCreateRequestDto;
import by.zemich.userservice.application.query.dto.UserResponseDto;
import by.zemich.userservice.api.rest.mapper.UserCommandMapper;
import by.zemich.userservice.api.rest.mapper.UserMapper;
import by.zemich.userservice.application.command.UserCommandService;
import by.zemich.userservice.domain.models.commands.CreateUserCommand;
import by.zemich.userservice.domain.models.user.entity.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;


@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserCommandController {
    private final UserCommandService userCommandService;

    @PostMapping
    public ResponseEntity<UserResponseDto> register(@Valid @RequestBody UserCreateRequestDto dto) {
        CreateUserCommand command = UserCommandMapper.map(dto);
        User newUser = userCommandService.handle(command);
        UserResponseDto response = UserMapper.map(newUser);
        String location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.getId())
                .toUriString();
        return ResponseEntity.created(URI.create(location)).body(response);
    }
}
