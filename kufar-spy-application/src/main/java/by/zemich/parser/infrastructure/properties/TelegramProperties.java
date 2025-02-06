package by.zemich.parser.infrastructure.properties;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@ConfigurationProperties(prefix = "telegram")
@Getter @Setter
@AllArgsConstructor
public class TelegramProperties {
    private Map<String, String> bots;

}
