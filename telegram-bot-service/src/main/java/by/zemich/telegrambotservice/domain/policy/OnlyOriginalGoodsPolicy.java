package by.zemich.telegrambotservice.domain.policy;


import by.zemich.telegrambotservice.domain.model.KufarAdvertisement;
import by.zemich.telegrambotservice.domain.policy.api.Policy;

import java.util.regex.Pattern;

public class OnlyOriginalGoodsPolicy implements Policy<KufarAdvertisement> {

    private static final Pattern DEFECT_PATTERN = Pattern.compile(
            "(?i)(продам|продаётся)?\\s*(полная|точная)?\\s*(копи[ияю]|реплика|реплик[уа]|паль|под(д)?елка)"
    );

    private static final Pattern ORIGINAL_PATTERN = Pattern.compile(
            "(?i)(оригинал|не реплика)"
    );

    @Override
    public boolean isSatisfiedBy(KufarAdvertisement kufarAdvertisement) {
        String details = kufarAdvertisement.getDetails().trim().toLowerCase();
        if (isOriginal(details)) return true;
        return !containsDataAboutUnoriginality(details);
    }

    private boolean containsDataAboutUnoriginality(String adDetails) {
        if (adDetails == null || adDetails.trim().isEmpty()) {
            return true;
        }
        return DEFECT_PATTERN.matcher(adDetails).find();
    }

    private boolean isOriginal(String adDetails) {
        if (adDetails == null || adDetails.trim().isEmpty()) {
            return true;
        }
        return ORIGINAL_PATTERN.matcher(adDetails).find();
    }
}
