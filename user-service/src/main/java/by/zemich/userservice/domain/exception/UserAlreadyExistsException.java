package by.zemich.userservice.domain.exception;

public class UserAlreadyExistsException extends EntityNotFoundException {

    public UserAlreadyExistsException(String email) {
        super("User with email " + email + " already exists");
    }
}
