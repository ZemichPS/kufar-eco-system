package by.zemich.telegrambotservice.application.service.scenarious.api;

import by.zemich.telegrambotservice.application.service.api.TelegramSender;
import lombok.RequiredArgsConstructor;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.action.Action;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

@RequiredArgsConstructor
public abstract class BaseRenderAction<S extends Enum<S>, E> implements Action<S, E>, StateHandleable<S> {

    protected final String ACTION_TEXT;
    protected final TelegramSender<SendMessage> telegramSender;

    protected StateMachine<S, E> getStateMachine(StateContext<S, E> context) {
        return context.getStateMachine();
    }

    protected SendMessage createMessage(Long chatId,
                                        String text,
                                        ReplyKeyboard reply
    ) {
        return SendMessage.builder()
                .chatId(chatId)
                .text(text)
                .replyMarkup(reply)
                .build();
    }

    protected SendMessage createMessage(Long chatId,
                                        String text
    ) {
        return SendMessage.builder()
                .chatId(chatId)
                .text(text)
                .build();
    }

    protected void saveTriggerEvent(StateContext<S, E> context) {
        E event = context.getEvent();
        context.getExtendedState().getVariables().put("previousEvent", event);
    }

    protected Long getChatId(StateContext<S, E> context) {
        return StateMachineContextHelper.getChatId(context);
    }


}
