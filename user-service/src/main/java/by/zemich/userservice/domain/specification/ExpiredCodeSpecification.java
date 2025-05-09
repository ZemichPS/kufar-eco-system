package by.zemich.userservice.domain.specification;

import by.zemich.userservice.domain.exception.GenericSpecificationException;
import by.zemich.userservice.domain.model.code.entity.EmailConfirmationCode;

import java.time.LocalDateTime;

public class ExpiredCodeSpecification extends AbstractSpecification<EmailConfirmationCode> {

    @Override
    public boolean isSatisfiedBy(EmailConfirmationCode emailConfirmationCode) {
        return LocalDateTime.now().isBefore(emailConfirmationCode.getExpiresAt());
    }

    @Override
    public void check(EmailConfirmationCode emailConfirmationCode) throws GenericSpecificationException {
        if (!isSatisfiedBy(emailConfirmationCode)) throw new GenericSpecificationException("confirmation code expired");
    }
}
