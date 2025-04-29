package by.zemich.device_catalog_service.infrastructure.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

@Configuration
@Slf4j
public class RestClientConfig {

    @Bean
    public RestClient restClient() {
        return RestClient.builder()
                .requestFactory(new JdkClientHttpRequestFactory()) // Java 21+ (для HTTP/2)
                .defaultHeader("Accept", "application/json")
                .defaultHeader("Content-Type", "application/json")
                .requestInterceptor((request, body, execution) -> {
                    logRequest(request); // Логирование запроса
                    return execution.execute(request, body);
                })
                .build();
    }

    private void logRequest(HttpRequest request) {
        log.info("Request: {} {}", request.getMethod(), request.getURI());
    }
}