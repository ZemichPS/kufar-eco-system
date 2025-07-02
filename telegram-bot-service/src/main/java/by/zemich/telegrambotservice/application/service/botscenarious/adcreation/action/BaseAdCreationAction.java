package by.zemich.telegrambotservice.application.service.botscenarious.adcreation.action;

import by.zemich.telegrambotservice.application.service.api.TelegramSender;
import by.zemich.telegrambotservice.application.service.botscenarious.adcreation.AdCreationState;
import by.zemich.telegrambotservice.application.service.botscenarious.adcreation.AddAdvertisementEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.action.Action;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

@RequiredArgsConstructor
public abstract class BaseAdCreationAction implements Action<AdCreationState, AddAdvertisementEvent> {

    protected final String ACTION_TEXT;
    protected final TelegramSender<SendMessage> telegramSender;

    protected StateMachine<?,?> getStateMachine(StateContext<AdCreationState, AddAdvertisementEvent> context) {
        return context.getStateMachine();
    }

    protected abstract void fillInAd(StateMachine<?,?> stateMachine);

    protected SendMessage createMessage(Long chatId,
                                        String text,
                                        ReplyKeyboard reply
    ){
        return SendMessage.builder()
                .chatId(chatId)
                .text(text)
                .replyMarkup(reply)
                .build();
    }
}
