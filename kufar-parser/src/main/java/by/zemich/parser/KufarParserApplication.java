package by.zemich.parser;

import by.zemich.parser.infrastructure.properties.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@EnableConfigurationProperties({
        CategoryParseListProperties.class
})
@EnableDiscoveryClient
@SpringBootApplication
public class KufarParserApplication {

    public static void main(String[] args) {
        SpringApplication.run(KufarParserApplication.class, args);
    }

}
