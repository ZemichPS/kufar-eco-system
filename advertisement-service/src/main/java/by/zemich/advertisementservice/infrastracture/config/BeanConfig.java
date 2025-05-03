package by.zemich.advertisementservice.infrastracture.config;

import by.zemich.advertisementservice.application.ports.input.*;
import by.zemich.advertisementservice.application.ports.output.AdvertisementEventOutputPort;
import by.zemich.advertisementservice.application.ports.output.AdvertisementPerststenceOutputPort;
import by.zemich.advertisementservice.application.ports.output.CategoryAttributeOutputPort;
import by.zemich.advertisementservice.application.ports.output.CategoryPersistenceOutputPort;
import by.zemich.advertisementservice.application.usecases.*;
import by.zemich.advertisementservice.domain.repository.AdvertisementFullTextQueryRepository;
import by.zemich.advertisementservice.domain.repository.AdvertisementQueryRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public AdvertisementQueryUseCases advertisementQueryUseCases(AdvertisementQueryRepository advertisementQueryRepository,
                                                                 AdvertisementFullTextQueryRepository advertisementFullTextQueryRepository) {
        return new AdvertisementQueryInputPort(advertisementQueryRepository, advertisementFullTextQueryRepository);
    }

    @Bean
    public AdvertisementCommandUseCases advertisementUseCase(
            AdvertisementPerststenceOutputPort advertisementPerststenceOutputPort,
            AdvertisementEventOutputPort advertisementEventOutputPort
    ) {
        return new AdvertisementInputPort(advertisementPerststenceOutputPort, advertisementEventOutputPort);
    }

    @Bean
    public CategoryCommandUseCase categoryUseCase(CategoryPersistenceOutputPort categoryPersistenceOutputPort, CategoryAttributeOutputPort categoryAttributeOutputPort) {
        return new CategoryCommandInputPort(categoryPersistenceOutputPort, categoryAttributeOutputPort);
    }

    @Bean
    public CategoryAttributeUseCase categoryAttributeUseCase(
            CategoryAttributeOutputPort categoryAttributeOutputPort,
            CategoryPersistenceOutputPort categoryPersistenceOutputPort) {
        return new CategoryAttributeInputPort(categoryAttributeOutputPort, categoryPersistenceOutputPort);
    }

    @Bean
    public CategoryQueryUseCase categoryQueryUseCase(CategoryPersistenceOutputPort categoryPersistenceOutputPort) {
        return new CategoryQueryInputPort(categoryPersistenceOutputPort);
    }
}
