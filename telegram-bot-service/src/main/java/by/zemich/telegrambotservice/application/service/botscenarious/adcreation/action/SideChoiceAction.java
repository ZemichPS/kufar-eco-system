package by.zemich.telegrambotservice.application.service.botscenarious.adcreation.action;

import by.zemich.telegrambotservice.application.service.api.TelegramSender;
import by.zemich.telegrambotservice.application.service.botscenarious.adcreation.AdCreationState;
import by.zemich.telegrambotservice.application.service.botscenarious.adcreation.AddAdvertisementEvent;
import by.zemich.telegrambotservice.application.service.botscenarious.api.KeyboardUtil;
import by.zemich.telegrambotservice.application.service.botscenarious.api.StateMachineContextHelper;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.List;

@Component
public class SideChoiceAction extends BaseAdCreationAction {
    public SideChoiceAction(TelegramSender<SendMessage> telegramSender) {
        super("Вы что-то ищите или желаете продать?", telegramSender);
    }

    @Override
    public void execute(StateContext<AdCreationState, AddAdvertisementEvent> context) {
        StateMachine<?, ?> sm = context.getStateMachine();
        fillInAd(sm);
        Long chatId = StateMachineContextHelper.getChatId(sm);
        InlineKeyboardMarkup inlineKeyboardMarkup = KeyboardUtil.createInlineKeyboardMarkup(List.of("Ищу", "Продаю"), 2);
        SendMessage message = createMessage(chatId, this.ACTION_TEXT, inlineKeyboardMarkup);
        telegramSender.send(message);
    }

    @Override
    protected void fillInAd(StateMachine<?, ?> stateMachine) {

    }
}
