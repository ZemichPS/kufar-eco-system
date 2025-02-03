package by.zemich.telegrambotservice.infrastructure.properties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@ConfigurationProperties(prefix = "telegram")
@Getter @Setter
@AllArgsConstructor
public class TelegramProperties {
    private Map<String, String> bots;

}
