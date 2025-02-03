package by.zemich.analyticsservice.domain.policy;


import by.zemich.analyticsservice.domain.policy.api.Policy;

public class MinimumRequredAmountOfDataForMarketPriceCountingPolicy implements Policy<Integer> {
    private final Integer REQUIRED_ARRAY_SIZE = 20;

    public MinimumRequredAmountOfDataForMarketPriceCountingPolicy() {
    }

    @Override
    public boolean isSatisfiedBy(Integer currentSize) {
        return currentSize >= REQUIRED_ARRAY_SIZE;
    }
}
