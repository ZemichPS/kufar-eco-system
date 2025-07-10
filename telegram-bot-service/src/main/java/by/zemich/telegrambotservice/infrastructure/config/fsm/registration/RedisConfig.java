package by.zemich.telegrambotservice.infrastructure.config.fsm.registration;

import by.zemich.telegrambotservice.application.service.scenarious.registration.event.UserRegistrationEvent;
import by.zemich.telegrambotservice.application.service.scenarious.registration.state.UserRegistrationState;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.statemachine.StateMachinePersist;
import org.springframework.statemachine.data.redis.RedisPersistingStateMachineInterceptor;
import org.springframework.statemachine.data.redis.RedisStateMachineContextRepository;
import org.springframework.statemachine.data.redis.RedisStateMachinePersister;
import org.springframework.statemachine.data.redis.RedisStateMachineRepository;
import org.springframework.statemachine.persist.RepositoryStateMachinePersist;
import org.springframework.statemachine.persist.StateMachineRuntimePersister;

import java.time.Duration;

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
