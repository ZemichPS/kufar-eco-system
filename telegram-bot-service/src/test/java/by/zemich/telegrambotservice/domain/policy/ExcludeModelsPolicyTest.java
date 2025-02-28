package by.zemich.telegrambotservice.domain.policy;

import by.zemich.telegrambotservice.domain.model.KufarAdvertisement;
import by.zemich.telegrambotservice.domain.policy.api.ExcludeModelsPolicy;
import by.zemich.telegrambotservice.mocks.AdvertisementInjectionExtension;
import by.zemich.telegrambotservice.mocks.KufarAdvertisementInjection;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@ExtendWith(AdvertisementInjectionExtension.class)
class ExcludeModelsPolicyTest {

    @KufarAdvertisementInjection
    private KufarAdvertisement kufarAdvertisement;
    private static ExcludeModelsPolicy excludeModelsPolicy;

    @ParameterizedTest
    @MethodSource("getIrrelevantAdvertisementModelParameters")
    void givenIrrelevantModels_whenIsSatisfiedBy_thenReturnFalse(KufarAdvertisement.Parameter parameter) {
        kufarAdvertisement.getParameters().add(parameter);
        Assertions.assertFalse(excludeModelsPolicy.isSatisfiedBy(kufarAdvertisement));

    }

    @ParameterizedTest
    @MethodSource("getRelevantAdvertisementModelParameters")
    void givenRelevantModels_whenIsSatisfiedBy_thenReturnTrue(KufarAdvertisement.Parameter parameter) {
        kufarAdvertisement.getParameters().add(parameter);
        Assertions.assertTrue(excludeModelsPolicy.isSatisfiedBy(kufarAdvertisement));
    }

    static Stream<Arguments> getIrrelevantAdvertisementModelParameters() {
        return Stream.of(
                Arguments.of(KufarAdvertisement.Parameter.builder().value("iPhone 6").identity("phones_model").label("Модель").build()),
                Arguments.of(KufarAdvertisement.Parameter.builder().value("iPhone X").identity("phones_model").label("Модель").build()),
                Arguments.of(KufarAdvertisement.Parameter.builder().value("iPhone XR").identity("phones_model").label("Модель").build()),
                Arguments.of(KufarAdvertisement.Parameter.builder().value("iPhone 6s").identity("phones_model").label("Модель").build())
        );
    }

    @NotNull
    static Stream<Arguments> getRelevantAdvertisementModelParameters() {
        return Stream.of(
                Arguments.of(KufarAdvertisement.Parameter.builder().value("iPhone 12 mini").identity("phones_model").label("Модель").build()),
                Arguments.of(KufarAdvertisement.Parameter.builder().value("iPhone 15").identity("phones_model").label("Модель").build()),
                Arguments.of(KufarAdvertisement.Parameter.builder().value("iPhone 14 pro").identity("phones_model").label("Модель").build()),
                Arguments.of(KufarAdvertisement.Parameter.builder().value("iPhone 13").identity("phones_model").label("Модель").build()),
                Arguments.of(KufarAdvertisement.Parameter.builder().value("iPhone 1").identity("phones_model").label("Модель").build()),
                Arguments.of(KufarAdvertisement.Parameter.builder().value("iPhone 11").identity("phones_model").label("Модель").build())
        );
    }

    @BeforeAll
    static void setup() {
        excludeModelsPolicy = new ExcludeModelsPolicy(List.of(
                "iPhone 5",
                "iPhone 5s",
                "iPhone 5G",
                "iPhone 6",
                "iPhone 6s",
                "iPhone 6 Plus",
                "iPhone 7",
                "iPhone 7 Plus",
                "iPhone 8",
                "iPhone 8 Plus",
                "iPhone X",
                "iPhone XR",
                "iPhone XS",
                "iPhone XS Max",
                "iPhone SE (2020)"
        ));
    }


}