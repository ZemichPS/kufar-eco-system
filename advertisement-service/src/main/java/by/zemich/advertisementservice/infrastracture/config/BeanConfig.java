package by.zemich.advertisementservice.infrastracture.config;

import by.zemich.advertisementservice.application.ports.input.AdvertisementInputPort;
import by.zemich.advertisementservice.application.ports.input.CategoryAttributeInputPort;
import by.zemich.advertisementservice.application.ports.input.CategoryInputPort;
import by.zemich.advertisementservice.application.ports.output.AdvertisementEventOutputPort;
import by.zemich.advertisementservice.application.ports.output.AdvertisementOutputPort;
import by.zemich.advertisementservice.application.ports.output.CategoryAttributeOutputPort;
import by.zemich.advertisementservice.application.ports.output.CategoryPersistenceOutputPort;
import by.zemich.advertisementservice.application.usecases.AdvertisementUseCase;
import by.zemich.advertisementservice.application.usecases.CategoryAttributeUseCase;
import by.zemich.advertisementservice.application.usecases.CategoryUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public AdvertisementUseCase advertisementUseCase(
            CategoryPersistenceOutputPort categoryPersistenceOutputPort,
            AdvertisementOutputPort advertisementOutputPort,
            AdvertisementEventOutputPort advertisementEventOutputPort
    ) {
        return new AdvertisementInputPort(categoryPersistenceOutputPort, advertisementOutputPort, advertisementEventOutputPort);
    }

    @Bean
    public CategoryUseCase categoryUseCase(CategoryPersistenceOutputPort categoryPersistenceOutputPort) {
        return new CategoryInputPort(categoryPersistenceOutputPort);
    }

    @Bean
    public CategoryAttributeUseCase categoryAttributeUseCase(CategoryAttributeOutputPort categoryAttributeOutputPort) {
        return new CategoryAttributeInputPort(categoryAttributeOutputPort);
    }
}
