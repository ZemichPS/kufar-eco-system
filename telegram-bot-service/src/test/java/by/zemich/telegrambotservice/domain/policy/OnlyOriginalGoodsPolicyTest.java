package by.zemich.telegrambotservice.domain.policy;

import by.zemich.telegrambotservice.domain.model.KufarAdvertisement;
import by.zemich.telegrambotservice.mocks.AdvertisementInjectionExtension;
import by.zemich.telegrambotservice.mocks.KufarAdvertisementInjection;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;


@ExtendWith(AdvertisementInjectionExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class OnlyOriginalGoodsPolicyTest {

    @KufarAdvertisementInjection(emptyParam = false)
    private KufarAdvertisement advertisement;

    private static OnlyOriginalGoodsPolicy policy;

    @ParameterizedTest
    @MethodSource("getRelevantAdDescription")
    void givenRelevantAdDescription_whenIsSatisfiedBy_thenReturnTrue(String details) {
        advertisement.setDetails(details);
    }

    @ParameterizedTest
    @MethodSource("getIrrelevantAdDescription")
    void givenIrrelevantAdDescription_whenIsSatisfiedBy_thenReturnTrue(String details) {
        advertisement.setDetails(details);
    }

    private static Stream<Arguments> getRelevantAdDescription() {
        return Stream.of(
                Arguments.of("100 % оригинал"),
                Arguments.of("не копия"),
                Arguments.of("не паль")
        );
    }

    private static Stream<Arguments> getIrrelevantAdDescription() {
        return Stream.of(
                Arguments.of("паль"),
                Arguments.of("копия"),
                Arguments.of("хорошая копия")
        );
    }

    @BeforeAll
    public static void setup(){
        policy = new OnlyOriginalGoodsPolicy();
    }
}