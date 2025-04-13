package by.zemich.userservice.domain.models.exceptions;

public class UserNotFoundException extends EntityNotFoundException {

    public UserNotFoundException(String id) {
        super("User with id " + id + " is nowhere to be found");

    }
}
