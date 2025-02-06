package by.zemich.parser;

import by.zemich.parser.infrastructure.properties.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@EnableConfigurationProperties({
        TelegramProperties.class,
        MinioProperties.class,
        CategoryParseListProperties.class,
        ChannelsBotProperties.class,
        ChannelsDelayProperty.class
}
)
@EnableDiscoveryClient
@SpringBootApplication
public class KufarApplication {

    public static void main(String[] args) {
        SpringApplication.run(KufarApplication.class, args);
    }

}
