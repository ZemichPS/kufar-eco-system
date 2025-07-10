package by.zemich.telegrambotservice.infrastructure.config.fsm;

import by.zemich.telegrambotservice.application.service.scenarious.ScenarioType;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.StateMachineFactory;

import java.util.EnumMap;
import java.util.Map;

@Configuration
@Log4j2
public class FSMCommonConfig {

    @Bean
    public Map<ScenarioType, StateMachineFactory<?, ?>> fsmFactoryRegistry(ApplicationContext context) {

        Map<ScenarioType, StateMachineFactory<?, ?>> registry = new EnumMap<>(ScenarioType.class);

        context.getBeansOfType(StateMachineFactory.class).forEach((beanName, value) -> {
            try {
                ScenarioType scenario = ScenarioType.valueOf(beanName);
                registry.put(scenario, value);
            } catch (IllegalArgumentException ex) {
                log.info("⚠️ Не удалось сопоставить фабрику {} с ScenarioType%n", beanName);
            }
        });
        return registry;
    }

}
