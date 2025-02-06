package by.zemich.parser.domain.model.criterias;

import by.zemich.parser.domain.model.Advertisement;

public class NotCompanyCriteria implements Criteria {

    @Override
    public boolean isSatisfied(Advertisement advertisement) {
        return !advertisement.isCompanyAd();
    }
}
