package by.zemich.userservice.api.rest.controllers;

import by.zemich.userservice.application.query.OrganizationQueryService;
import by.zemich.userservice.application.query.UserQueryService;
import by.zemich.userservice.domain.dto.OrganizationDto;
import by.zemich.userservice.domain.dto.OrganizationFullDto;
import by.zemich.userservice.domain.model.organization.entity.Organization;
import by.zemich.userservice.domain.model.organization.vo.OrganizationId;
import by.zemich.userservice.domain.queries.GetUserByTelegramIdQuery;
import by.zemich.userservice.domain.queries.GetUserByUserId;
import by.zemich.userservice.domain.model.user.vo.UserId;
import by.zemich.userservice.domain.dto.UserFullRecord;
import by.zemich.userservice.infrastructure.security.annotations.CurrentUserId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserQueryController {
    private final UserQueryService userQueryService;
    private final OrganizationQueryService organizationQueryService;

    @GetMapping
    public ResponseEntity<List<UserFullRecord>> getAllUsers() {
        List<UserFullRecord> response = userQueryService.getAllRecords();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{telegramId}")
    public ResponseEntity<UserFullRecord> getUserByTelegramId(@PathVariable String telegramId) {
        if (telegramId == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity not found");
        UserFullRecord response = userQueryService.getByTelegramId(new GetUserByTelegramIdQuery(telegramId));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/me")
    public ResponseEntity<UserFullRecord> getUserPage(@CurrentUserId UserId userId) {
        UserFullRecord response = userQueryService.getFullRecordById(new GetUserByUserId(userId));
        return ResponseEntity.ok(response);
    }

}
