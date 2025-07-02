package by.zemich.telegrambotservice.infrastructure.config.fsm;

import by.zemich.telegrambotservice.application.service.botscenarious.ScenarioType;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.StateMachineFactory;

import java.util.EnumMap;
import java.util.Map;

@Configuration
public class FSMCommonConfig {

    @Bean
    public Map<ScenarioType, StateMachineFactory<?, ?>> fsmFactoryRegistry(ApplicationContext context) {

        Map<ScenarioType, StateMachineFactory<?, ?>> registry = new EnumMap<>(ScenarioType.class);

        context.getBeansOfType(StateMachineFactory.class).entrySet().stream()
                .forEach(entry -> {
                    String beanName = entry.getKey(); //
                    StateMachineFactory<?, ?> factory = entry.getValue();
                    try {
                        ScenarioType scenario = ScenarioType.valueOf(beanName);
                        registry.put(scenario, factory);
                    } catch (IllegalArgumentException ex) {
                        System.err.printf("⚠️ Не удалось сопоставить фабрику [%s] с ScenarioType%n", beanName);
                    }
                });

        return registry;
    }

}
