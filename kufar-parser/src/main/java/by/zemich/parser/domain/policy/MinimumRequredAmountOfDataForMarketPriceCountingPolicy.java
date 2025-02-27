package by.zemich.parser.domain.policy;

import by.zemich.parser.domain.policy.api.Policy;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@RefreshScope
public class MinimumRequredAmountOfDataForMarketPriceCountingPolicy implements Policy<Integer> {

    @Value("${price-analyzing.min-array-prices}")
    private Integer NEEDED_ARRAY_SIZE;

    @Override
    public boolean isSatisfiedBy(Integer currentSize) {
        return currentSize >= NEEDED_ARRAY_SIZE;
    }
}
