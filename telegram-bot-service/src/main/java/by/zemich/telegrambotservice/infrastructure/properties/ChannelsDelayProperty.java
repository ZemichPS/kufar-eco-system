package by.zemich.telegrambotservice.infrastructure.properties;

import by.zemich.telegrambotservice.application.service.channels.api.AbstractTelegramChannel;
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

    public Long getDelayDurationByChannel(AbstractTelegramChannel abstractTelegramChannel) {
        return channelsDelay.getOrDefault(abstractTelegramChannel.getChannelId(), 0L);
    }
}
