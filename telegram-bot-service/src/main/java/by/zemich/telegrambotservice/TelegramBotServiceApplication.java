package by.zemich.telegrambotservice;

import by.zemich.telegrambotservice.infrastructure.properties.ChannelsBotProperties;
import by.zemich.telegrambotservice.infrastructure.properties.ChannelsDelayProperty;
import by.zemich.telegrambotservice.infrastructure.properties.MinioProperties;
import by.zemich.telegrambotservice.infrastructure.properties.TelegramProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
@EnableConfigurationProperties({
        TelegramProperties.class,
        ChannelsBotProperties.class,
        ChannelsDelayProperty.class,
        MinioProperties.class
})
public class TelegramBotServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TelegramBotServiceApplication.class, args);
    }

}
