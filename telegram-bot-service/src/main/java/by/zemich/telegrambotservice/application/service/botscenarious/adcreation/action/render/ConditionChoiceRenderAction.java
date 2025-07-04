package by.zemich.telegrambotservice.application.service.botscenarious.adcreation.action.render;

import by.zemich.telegrambotservice.application.service.api.TelegramSender;
import by.zemich.telegrambotservice.application.service.botscenarious.adcreation.AdCreationState;
import by.zemich.telegrambotservice.application.service.botscenarious.adcreation.AddAdvertisementEvent;
import by.zemich.telegrambotservice.application.service.botscenarious.api.KeyboardUtil;
import by.zemich.telegrambotservice.application.service.botscenarious.api.StateMachineContextHelper;
import by.zemich.telegrambotservice.domain.dto.AdvertisementDraftDto;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.List;

@Component
public class ConditionChoiceRenderAction extends AdCreationRenderAction {

    public ConditionChoiceRenderAction(TelegramSender<SendMessage> telegramSender) {
        super("Выберите состояние", telegramSender);
    }

    @Override
    public void execute(StateContext<AdCreationState, AddAdvertisementEvent> context) {
        StateMachine<AdCreationState, AddAdvertisementEvent> sm = getStateMachine(context);
        Long chatId = StateMachineContextHelper.getChatId(sm);
        fillInAd(sm);

        InlineKeyboardMarkup inlineKeyboardMarkup = KeyboardUtil.createInlineKeyboardMarkup(
                List.of("Новое", "Б.у.", "Неисправно"), 1
        );
        SendMessage message = createMessage(chatId, ACTION_TEXT, inlineKeyboardMarkup);
        telegramSender.send(message);
    }

    @Override
    protected void fillInAd(StateMachine<AdCreationState, AddAdvertisementEvent> stateMachine) {
        String adCategory = StateMachineContextHelper.getPreviousStageText(stateMachine);
        AdvertisementDraftDto adDraft = StateMachineContextHelper.getAdDraft(stateMachine);
        adDraft.setCategory(adCategory);
        StateMachineContextHelper.setAdDraft(stateMachine, adDraft);
    }
}
