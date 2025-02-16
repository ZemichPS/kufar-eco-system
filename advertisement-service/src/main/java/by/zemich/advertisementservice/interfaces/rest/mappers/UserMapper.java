package by.zemich.advertisementservice.interfaces.rest.mappers;

import by.zemich.advertisementservice.domain.entity.User;
import by.zemich.advertisementservice.interfaces.rest.data.response.UserDto;

public class UserMapper {
    public static UserDto mapToDto(User user){
        return UserDto.builder()
                .username(user.getUsername())
                .telegramChatId(user.getTelegramUserData().getChatId())
                .telegramUsername(user.getTelegramUserData().getUsername())
                .build();
    }
}
