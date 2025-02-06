package by.zemich.parser.infrastructure.properties;

import by.zemich.parser.application.service.channels.api.TelegramChannel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import java.util.Map;

@Getter @Setter
@AllArgsConstructor
@ConfigurationProperties(prefix = "publish.delays")
@RefreshScope
public class ChannelsDelayProperty {
    private Map<String, Long> channelsDelay;

    public Long getDelayDurationByChannel(TelegramChannel telegramChannel) {
        return channelsDelay.getOrDefault(telegramChannel.getChannelId(), 0L);
    }
}
