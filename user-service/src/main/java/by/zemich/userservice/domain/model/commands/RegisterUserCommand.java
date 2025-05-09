package by.zemich.userservice.domain.model.commands;

public record RegisterUserCommand(
        String role,
        String username,
        String firstname,
        String lastname,
        String email,
        String phoneNumber,
        String rawPassword
) {
}
