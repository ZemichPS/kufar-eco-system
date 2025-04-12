package by.zemich.userservice.service.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String identity) {
        super("User with telegram identity " + identity + " is nowhere to be found");
    }
}
