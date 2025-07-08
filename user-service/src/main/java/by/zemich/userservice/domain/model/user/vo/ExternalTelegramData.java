package by.zemich.userservice.domain.model.user.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExternalTelegramData {
    private Long userId;
    private Long chatId;
    private String username;
}
