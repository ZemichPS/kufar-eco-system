package by.zemich.telegrambotservice.infrastructure.config;

import by.zemich.telegrambotservice.application.service.bots.DialogBot;
import by.zemich.telegrambotservice.application.service.scenarious.api.UpdateHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class DialogBotApiConfig {

    @Bean
    public TelegramBotsApi telegramBotsApi(DialogBot dialogBot) throws TelegramApiException {
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(dialogBot);
        return botsApi;
    }


}
