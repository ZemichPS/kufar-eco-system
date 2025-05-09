package by.zemich.userservice.domain.exception;

public class OrganizationNotFoundException extends EntityNotFoundException {

    public OrganizationNotFoundException(String id) {
        super("Organization with id " + id + " is nowhere to be found");

    }
}
