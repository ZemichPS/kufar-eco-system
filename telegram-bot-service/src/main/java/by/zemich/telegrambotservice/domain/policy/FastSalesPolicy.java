package by.zemich.telegrambotservice.domain.policy;


import by.zemich.telegrambotservice.domain.model.KufarAdvertisement;
import by.zemich.telegrambotservice.domain.policy.api.Policy;

import java.util.regex.Pattern;

public class FastSalesPolicy implements Policy<KufarAdvertisement> {

    private final Pattern DETECT = Pattern.compile(
            "(?i)(срочн(о|а|ая|))[^\\w\\s]*\\s*(пр[ао]да(жа|м|[её]тся))?"
    );


    @Override
    public boolean isSatisfiedBy(KufarAdvertisement kufarAdvertisement) {
        String details = kufarAdvertisement.getDetails();
        return DETECT.matcher(details).find();
    }
}
