package by.zemich.userservice.domain.exception;

public class UserAlreadyExistsException extends EntityNotFoundException {

    public UserAlreadyExistsException(String identity) {
        super("User with identity " + identity + " already exists");
    }
}
