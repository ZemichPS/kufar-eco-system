package by.zemich.userservice.domain.specification;

import by.zemich.userservice.domain.exception.GenericSpecificationException;
import by.zemich.userservice.domain.model.organization.entity.Organization;
import by.zemich.userservice.domain.model.user.vo.UserId;

public class UserOrganizationOwnerSpecification extends AbstractSpecification<Organization> {

    private final UserId ownerId;

    @Override
    public boolean isSatisfiedBy(Organization organization) {
        return organization.getOwnerId().equals(ownerId);
    }

    public UserOrganizationOwnerSpecification(UserId ownerId) {
        this.ownerId = ownerId;
    }

    @Override
    public void check(Organization organization) throws GenericSpecificationException {
        if(!isSatisfiedBy(organization)) throw new GenericSpecificationException("User must own this organization");
    }
}
