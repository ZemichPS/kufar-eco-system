package by.zemich.userservice.domain.models.exceptions;

public class OrganizationNotFoundException extends EntityNotFoundException {

    public OrganizationNotFoundException(String id) {
        super("Organization with id " + id + " is nowhere to be found");

    }
}
