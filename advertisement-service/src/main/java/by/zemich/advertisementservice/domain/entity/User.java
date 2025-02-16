package by.zemich.advertisementservice.domain.entity;


import by.zemich.advertisementservice.domain.valueobject.Id;
import by.zemich.advertisementservice.domain.valueobject.TelegramUserData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class User {
    private Id userId;
    private TelegramUserData telegramUserData;
    private String username;
}
