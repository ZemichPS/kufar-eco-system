package by.zemich.userservice.api.rest.controllers;

import by.zemich.userservice.api.rest.dto.UserCreateDto;
import by.zemich.userservice.api.rest.dto.UserResponseDto;
import by.zemich.userservice.api.rest.mapper.UserCommandMapper;
import by.zemich.userservice.api.rest.mapper.UserMapper;
import by.zemich.userservice.application.UserCommandService;
import by.zemich.userservice.application.UserQueryService;
import by.zemich.userservice.domain.models.commands.CreateUserCommand;
import by.zemich.userservice.domain.models.queries.GetUserByIdQuery;
import by.zemich.userservice.domain.models.queries.GetUserByTelegramIdQuery;
import by.zemich.userservice.domain.models.user.entity.User;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserCommandService userCommandService;
    private final UserQueryService userQueryService;


    @PostMapping
    @PermitAll
    public ResponseEntity<UserResponseDto> register(@Valid @RequestBody UserCreateDto dto) {
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

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAll(){
        List<UserResponseDto> response = userQueryService.getAll().stream()
                .map(UserMapper::map)
                .toList();
        return ResponseEntity.ok(response);
    }


    @GetMapping("/{telegramId}")
    @PostAuthorize("returnObject.email == authentication.name || hasRole('ADMIN')")
    public ResponseEntity<UserResponseDto> getByTelegramId(@PathVariable String telegramId) {
        if (telegramId == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity not found");
        User user = userQueryService.getByTelegramId(new GetUserByTelegramIdQuery(telegramId));
        return ResponseEntity.ok(UserMapper.map(user));
    }

    @GetMapping("/{userId}")
    @PostAuthorize("returnObject.email == authentication.name || hasRole('ADMIN')")
    public ResponseEntity<UserResponseDto> getById(@PathVariable UUID userId) {
        if (userId == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity not found");
        User user = userQueryService.getById(new GetUserByIdQuery(userId));
        return ResponseEntity.ok(UserMapper.map(user));
    }
}
