package by.zemich.telegrambotservice.application.service.openfeign;

import by.zemich.telegrambotservice.domain.dto.UserRegistrationDto;
import org.springframework.cloud.openfeign.FeignClient;

import java.util.UUID;

@FeignClient("getcategoriespath")
public interface UserServiceOpenFeign {
    UUID getUserIdByTelegramId(Long id);
    boolean existsByTelegramId(Long id);

    boolean saveUser(UserRegistrationDto userRegistration);
}
