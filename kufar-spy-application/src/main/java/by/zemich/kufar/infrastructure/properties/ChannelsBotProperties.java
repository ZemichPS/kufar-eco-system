package by.zemich.kufar.infrastructure.properties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

@ConfigurationProperties("channels.settings")
@Getter @Setter
@AllArgsConstructor
public class ChannelsBotProperties {
    private Map<String,List<String>> botsChannelsMap;

}
