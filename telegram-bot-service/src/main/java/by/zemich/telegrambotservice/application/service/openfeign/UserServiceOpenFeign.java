package by.zemich.telegrambotservice.application.service.openfeign;

import by.zemich.telegrambotservice.domain.dto.UserRegistrationDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.UUID;

@FeignClient("user-application")
public interface UserServiceOpenFeign {

    @RequestMapping(value = "/api/v1/users/{telegramId}", method = RequestMethod.GET)
    UUID getUserIdByTelegramId(@PathVariable Long telegramId);

    @RequestMapping(value = "/api/v1/users/exists", method = RequestMethod.GET)
    boolean existsByTelegramId(Long id);

    @RequestMapping(method = RequestMethod.POST,
            value = "/api/v1/users/telegram",
            consumes = "application/json"
    )
    boolean saveUser(UserRegistrationDto userRegistration);
}
