package by.zemich.userservice.domain.exception;

public class UserNotFoundException extends EntityNotFoundException {

    public UserNotFoundException(String id) {
        super("User with id " + id + " is nowhere to be found");

    }
}
