package by.zemich.userservice.infrastructure.security;


public record ChangePasswordRequest(String oldPassword, String newPassword) {
}
