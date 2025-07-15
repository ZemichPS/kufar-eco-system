package by.zemich.telegrambotservice.infrastructure.config;

import by.zemich.telegrambotservice.domain.model.UserSession;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.lettuce.core.ClientOptions;
import io.lettuce.core.SocketOptions;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

@Configuration
@EnableRedisRepositories
@RequiredArgsConstructor
public class AppRedisConfig {


    private final ObjectMapper objectMapper;

    @Bean
    public LettuceConnectionFactory lettuceConnectionFactory() {

        LettuceClientConfiguration clientConfig = LettuceClientConfiguration.builder()
                .commandTimeout(Duration.ofSeconds(2))
                .shutdownTimeout(Duration.ZERO)
                .clientOptions(ClientOptions.builder()
                        .autoReconnect(true) // Автоподключение при разрыве
                        .socketOptions(SocketOptions.builder()
                                .connectTimeout(Duration.ofSeconds(1))
                                .build())
                        .build()).build();

        RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration();
        redisConfig.setHostName("localhost");
        redisConfig.setPort(6379);
        redisConfig.setPassword("admin");
        return new LettuceConnectionFactory(redisConfig, clientConfig);
    }

    @Bean
    public RedisTemplate<String, UserSession> redisTemplate(LettuceConnectionFactory lettuceConnectionFactory) {
        RedisTemplate<String, UserSession> template = new RedisTemplate<>();
        template.setConnectionFactory(lettuceConnectionFactory);

        Jackson2JsonRedisSerializer<UserSession> serializer = new Jackson2JsonRedisSerializer<>(objectMapper, UserSession.class);

        template.setDefaultSerializer(serializer);
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(serializer);
        template.setHashValueSerializer(serializer);
        return template;
    }
}
