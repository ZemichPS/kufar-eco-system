package by.zemich.userservice.api.rest.controllers;

import by.zemich.userservice.api.rest.dto.UserCreateDto;
import by.zemich.userservice.api.rest.dto.UserDto;
import by.zemich.userservice.api.rest.dto.UserResponseDto;
import by.zemich.userservice.api.rest.mapper.UserCommandMapper;
import by.zemich.userservice.api.rest.mapper.UserMapper;
import by.zemich.userservice.application.UserCommandService;
import by.zemich.userservice.application.UserQueryService;
import by.zemich.userservice.domain.models.commands.CreateUserCommand;
import by.zemich.userservice.domain.models.queries.GetUserByIdQuery;
import by.zemich.userservice.domain.models.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;


@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserCommandService userCommandService;
    private final UserQueryService userQueryService;

    @PostMapping
    public ResponseEntity<UserResponseDto> register(@RequestBody UserCreateDto dto) {
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

    @GetMapping("/{telegramId}")
    public ResponseEntity<UserResponseDto> getByTelegramId(@PathVariable String telegramId) {
        if (telegramId == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity not found");
        User user = userQueryService.getByTelegramId(new GetUserByIdQuery(telegramId));
        return ResponseEntity.ok(UserMapper.map(user));
    }
}
