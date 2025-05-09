package by.zemich.userservice.api.rest.controllers;

import by.zemich.userservice.application.query.dto.GetUserByEmilQuery;
import by.zemich.userservice.application.query.UserQueryService;
import by.zemich.userservice.domain.model.queries.GetUserByTelegramIdQuery;
import by.zemich.userservice.infrastructure.persistence.jpa.repositories.projections.UserFullRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserQueryController {
    private final UserQueryService userQueryService;

    @GetMapping
    public ResponseEntity<List<UserFullRecord>> getAll() {
        List<UserFullRecord> response = userQueryService.getAllRecords();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{telegramId}")
    @PostAuthorize("returnObject.email == authentication.name || hasRole('ADMIN')")
    public ResponseEntity<UserFullRecord> getByTelegramId(@PathVariable String telegramId) {
        if (telegramId == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity not found");
        UserFullRecord response = userQueryService.getByTelegramId(new GetUserByTelegramIdQuery(telegramId));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/me")
    public ResponseEntity<UserFullRecord> getUserPage(@AuthenticationPrincipal UserDetails userDetails) {
        String email = userDetails.getUsername();
        UserFullRecord response = userQueryService.getFullRecordByEmail(new GetUserByEmilQuery(email));
        return ResponseEntity.ok(response);
    }
}
