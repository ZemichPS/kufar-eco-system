package by.zemich.userservice.infrastructure.security;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest loginRequest) {
        String token = authService.login(loginRequest.getEmail(), loginRequest.getPassword());
        return ResponseEntity.ok(new TokenResponse(token));
    }

    @PostMapping("/{userId}")
    public ResponseEntity<Void> changePassword(@PathVariable String userId,
                                               @RequestBody ChangePasswordRequest request) {
        authService.changePassword(userId, request.oldPassword(), request.newPassword());
        return ResponseEntity.noContent().build();
    }

}
