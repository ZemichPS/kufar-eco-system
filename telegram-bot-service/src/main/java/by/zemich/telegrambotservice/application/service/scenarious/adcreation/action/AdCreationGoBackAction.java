package by.zemich.telegrambotservice.application.service.scenarious.adcreation.action;

import by.zemich.telegrambotservice.application.service.api.TelegramSender;
import by.zemich.telegrambotservice.application.service.scenarious.adcreation.AdCreationState;
import by.zemich.telegrambotservice.application.service.scenarious.adcreation.AddAdvertisementEvent;
import by.zemich.telegrambotservice.application.service.scenarious.api.GoBackAction;
import lombok.RequiredArgsConstructor;
import org.springframework.statemachine.StateContext;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Component
@RequiredArgsConstructor
public class AdCreationGoBackAction implements GoBackAction<AdCreationState, AddAdvertisementEvent> {

    private final TelegramSender<SendMessage> telegramSender;

    @Override
    public void execute(StateContext<AdCreationState, AddAdvertisementEvent> context) {

        String errorMessage = context.getException().getMessage();

        SendMessage message = SendMessage.builder()
                .text("Ошибка ввода: " + errorMessage + ".\\n Повторите ввод.")
                .build();
        this.goBack(context);
        telegramSender.send(message);
    }
}
