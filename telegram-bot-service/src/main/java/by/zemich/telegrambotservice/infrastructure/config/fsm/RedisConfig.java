package by.zemich.telegrambotservice.infrastructure.config.fsm;

import by.zemich.telegrambotservice.application.service.scenarious.registration.event.UserRegistrationEvent;
import by.zemich.telegrambotservice.application.service.scenarious.registration.state.UserRegistrationState;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.statemachine.StateMachinePersist;
import org.springframework.statemachine.data.redis.RedisPersistingStateMachineInterceptor;
import org.springframework.statemachine.data.redis.RedisStateMachineContextRepository;
import org.springframework.statemachine.data.redis.RedisStateMachinePersister;
import org.springframework.statemachine.data.redis.RedisStateMachineRepository;
import org.springframework.statemachine.persist.RepositoryStateMachinePersist;
import org.springframework.statemachine.persist.StateMachineRuntimePersister;

@Configuration
@EnableRedisRepositories
public class RedisConfig {

    @Bean
    public StateMachineRuntimePersister<UserRegistrationState, UserRegistrationEvent, String> stateMachineRuntimePersister(RedisStateMachineRepository repository) {
        return new RedisPersistingStateMachineInterceptor<>(repository);
    }

    @Bean
    public RedisStateMachineContextRepository<UserRegistrationState, UserRegistrationEvent> redisStateMachineContextRepository(
            LettuceConnectionFactory lettuceConnectionFactory) {
        return new RedisStateMachineContextRepository<>(lettuceConnectionFactory);
    }

    @Bean
    public StateMachinePersist<UserRegistrationState, UserRegistrationEvent, String> stateMachinePersist(
            RedisStateMachineContextRepository<UserRegistrationState, UserRegistrationEvent> redisStateMachineContextRepository) {
        return new RepositoryStateMachinePersist<>(redisStateMachineContextRepository);
    }

    @Bean
    public RedisStateMachinePersister<UserRegistrationState, UserRegistrationEvent> redisStateMachinePersister(
            StateMachinePersist<UserRegistrationState, UserRegistrationEvent, String> stateMachinePersist) {
        return new RedisStateMachinePersister<>(stateMachinePersist);
    }
}
