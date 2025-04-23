package by.zemich.advertisementservice.infrastracture.config;

import by.zemich.advertisementservice.application.ports.input.AdvertisementInputPort;
import by.zemich.advertisementservice.application.ports.input.CategoryAttributeInputPort;
import by.zemich.advertisementservice.application.ports.input.CategoryCommandInputPort;
import by.zemich.advertisementservice.application.ports.output.AdvertisementEventOutputPort;
import by.zemich.advertisementservice.application.ports.output.AdvertisementOutputPort;
import by.zemich.advertisementservice.application.ports.output.CategoryAttributeOutputPort;
import by.zemich.advertisementservice.application.ports.output.CategoryPersistenceOutputPort;
import by.zemich.advertisementservice.application.usecases.AdvertisementCommandUseCases;
import by.zemich.advertisementservice.application.usecases.CategoryAttributeUseCase;
import by.zemich.advertisementservice.application.usecases.CategoryCommandUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public AdvertisementCommandUseCases advertisementUseCase(
            AdvertisementOutputPort advertisementOutputPort,
            AdvertisementEventOutputPort advertisementEventOutputPort
    ) {
        return new AdvertisementInputPort(advertisementOutputPort, advertisementEventOutputPort);
    }

    @Bean
    public CategoryCommandUseCase categoryUseCase(CategoryPersistenceOutputPort categoryPersistenceOutputPort, CategoryAttributeOutputPort categoryAttributeOutputPort) {
        return new CategoryCommandInputPort(categoryPersistenceOutputPort, categoryAttributeOutputPort);
    }

    @Bean
    public CategoryAttributeUseCase categoryAttributeUseCase(CategoryAttributeOutputPort categoryAttributeOutputPort) {
        return new CategoryAttributeInputPort(categoryAttributeOutputPort);
    }
}
