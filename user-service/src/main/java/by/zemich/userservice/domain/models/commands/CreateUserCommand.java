package by.zemich.userservice.domain.models.commands;

public record CreateUserCommand(
        String role,
        String firstname,
        String lastname,
        String email,
        String phoneNumber,
        String rawPassword
) {
}
