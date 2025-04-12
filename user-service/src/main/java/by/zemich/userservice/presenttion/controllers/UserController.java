package by.zemich.userservice.presenttion.controllers;

import by.zemich.userservice.dao.entities.User;
import by.zemich.userservice.presenttion.dto.UserCreateDto;
import by.zemich.userservice.presenttion.dto.UserDto;
import by.zemich.userservice.presenttion.mapper.UserMapper;
import by.zemich.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;


@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<URI> register(@RequestBody UserCreateDto dto) {
        User newUser = UserMapper.map(dto);
        User createdUser = userService.register(newUser);
        return ResponseEntity.created(URI.create(createdUser.getId().toString())).build();
    }

    @GetMapping("/{telegramId}")
    public ResponseEntity<UserDto> getByTelegramId(@PathVariable String telegramId) {
        if (telegramId == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity not found");
        User user = userService.getUserByTelegramId(telegramId);
        return ResponseEntity.ok(UserMapper.map(user));
    }
}
