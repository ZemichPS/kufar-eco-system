package by.zemich.advertisementservice.infrastracture.input.rest.data.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {
    private Long telegramChatId;
    private String telegramUsername;
    private String username;
}
