package by.zemich.advertisementservice.infrastracture.config;

import by.zemich.advertisementservice.application.ports.input.AdvertisementInputPort;
import by.zemich.advertisementservice.application.ports.output.AdvertisementEventOutputPort;
import by.zemich.advertisementservice.application.ports.output.AdvertisementOutputPort;
import by.zemich.advertisementservice.application.ports.output.CategoryOutputPort;
import by.zemich.advertisementservice.application.usecases.AdvertisementUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    AdvertisementUseCase advertisementUseCase(
            CategoryOutputPort categoryOutputPort,
            AdvertisementOutputPort advertisementOutputPort,
            AdvertisementEventOutputPort advertisementEventOutputPort
    ){
        return new AdvertisementInputPort(categoryOutputPort, advertisementOutputPort, advertisementEventOutputPort);
    }
}
