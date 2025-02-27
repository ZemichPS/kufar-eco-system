package by.zemich.telegrambotservice.domain.policy.api;

import by.zemich.telegrambotservice.domain.model.KufarAdvertisement;
import by.zemich.telegrambotservice.mocks.AdvertisementInjectionExtension;
import by.zemich.telegrambotservice.mocks.KufarAdvertisementInjection;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

@ExtendWith(AdvertisementInjectionExtension.class)
class ExcludeModelsPolicyTest {

    @KufarAdvertisementInjection
    private KufarAdvertisement kufarAdvertisement;
    private static ExcludeModelsPolicy excludeModelsPolicy;

    @ParameterizedTest
    @MethodSource("getAdvertisementParameters")
    void isSatisfiedBy(KufarAdvertisement.Parameter parameter) {
        kufarAdvertisement.getParameters().add(parameter);
        Assertions.assertFalse(excludeModelsPolicy.isSatisfiedBy(kufarAdvertisement));

    }

    static Stream<Arguments> getAdvertisementParameters() {
        return Stream.of(
                Arguments.of(KufarAdvertisement.Parameter.builder().value("iPhone 6").identity("phones_model").label("Модель").build()),
                Arguments.of(KufarAdvertisement.Parameter.builder().value("iPhone X").identity("phones_model").label("Модель").build()),
                Arguments.of(KufarAdvertisement.Parameter.builder().value("iPhone XR").identity("phones_model").label("Модель").build()),
                Arguments.of(KufarAdvertisement.Parameter.builder().value("iPhone 6s").identity("phones_model").label("Модель").build())
        );
    }

    @BeforeAll
    static void setup() {
        excludeModelsPolicy = new ExcludeModelsPolicy(List.of(
                "iPhone 5",
                "iPhone 5s",
                "iPhone 6",
                "iPhone 6s",
                "iPhone 6 Plus",
                "iPhone 7",
                "iPhone 7 Plus",
                "iPhone 8",
                "iPhone 8 Plus",
                "iPhone X",
                "iPhone XR",
                "IPhone XR",
                "IPhone XS",
                "iPhone SE (2020)"
        ));
    }


}