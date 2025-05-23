package by.zemich.parser.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

@Configuration
public class RetryConfig {
    @Bean
    public RetryTemplate retryTemplate() {
        RetryTemplate retryTemplate = new RetryTemplate();

        SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
        retryPolicy.setMaxAttempts(5);  // максимальное количество попыток
        FixedBackOffPolicy backOffPolicy = new FixedBackOffPolicy();
        backOffPolicy.setBackOffPeriod(5_000);  // задержка в миллисекундах
        retryTemplate.setRetryPolicy(retryPolicy);
        retryTemplate.setBackOffPolicy(backOffPolicy);

        return retryTemplate;
    }

    @Bean
    public RetryTemplate telegramRetryTemplate() {
        RetryTemplate retryTemplate = new RetryTemplate();
//        SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy(
//                5,
//                Map.of(
//                        TelegramApiException.class, true,
//                        TelegramApiRequestException.class, true,
//                        TelegramApiValidationException.class, true
//                )
//        );
//        retryTemplate.setRetryPolicy(retryPolicy);
        FixedBackOffPolicy backOffPolicy = new FixedBackOffPolicy();
        backOffPolicy.setBackOffPeriod(50_000);  // задержка в миллисекундах
        retryTemplate.setBackOffPolicy(backOffPolicy);
        return retryTemplate;
    }
}
